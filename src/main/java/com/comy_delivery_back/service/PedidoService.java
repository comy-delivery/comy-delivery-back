package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.AceitarPedidoRequestDTO;
import com.comy_delivery_back.dto.request.EntregaRequestDTO;
import com.comy_delivery_back.dto.request.ItemPedidoRequestDTO;
import com.comy_delivery_back.dto.request.PedidoRequestDTO;
import com.comy_delivery_back.dto.response.PedidoResponseDTO;
import com.comy_delivery_back.enums.StatusPedido;
import com.comy_delivery_back.exception.*;
import com.comy_delivery_back.model.*;
import com.comy_delivery_back.repository.*;
import com.comy_delivery_back.utils.DistanciaUtils;
import com.comy_delivery_back.utils.FreteUtils;
import com.comy_delivery_back.utils.StatusPedidoValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final RestauranteRepository restauranteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ProdutoRepository produtoRepository;
    private final CupomRepository cupomRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final AdicionalRepository adicionalRepository;
    private final EmailService emailService;
    private final EntregaService entregaService;

    public PedidoService(PedidoRepository pedidoRepository,
                         ClienteRepository clienteRepository,
                         RestauranteRepository restauranteRepository,
                         EnderecoRepository enderecoRepository,
                         ProdutoRepository produtoRepository,
                         CupomRepository cupomRepository,
                         ItemPedidoRepository itemPedidoRepository,
                         AdicionalRepository adicionalRepository, EmailService emailService, EntregaService entregaService) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
        this.enderecoRepository = enderecoRepository;
        this.produtoRepository = produtoRepository;
        this.cupomRepository = cupomRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.adicionalRepository = adicionalRepository;
        this.emailService = emailService;
        this.entregaService = entregaService;
    }

    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {
        log.info("A iniciar criação de pedido. Cliente: {}, Restaurante: {}", dto.cliente(), dto.restaurante());
        Cliente cliente = clienteRepository.findById(dto.cliente())
                .orElseThrow(() -> new ClienteNaoEncontradoException(dto.cliente()));

        Restaurante restaurante = restauranteRepository.findById(dto.restaurante())
                .orElseThrow(() -> new RestauranteNaoEncontradoException(dto.restaurante()));

        validarRestauranteDisponivel(restaurante);

        Endereco enderecoEntrega = enderecoRepository.findById(dto.enderecoEntregaId())
                .orElseThrow(() -> new EnderecoNaoEncontradoException(dto.enderecoEntregaId()));

        Endereco enderecoOrigem = enderecoRepository.findById(dto.enderecoOrigemId())
                .orElseThrow(() -> new EnderecoNaoEncontradoException(dto.enderecoOrigemId()));

        validarEnderecosPertencem(enderecoEntrega, enderecoOrigem, dto.cliente(), dto.restaurante());

        if (dto.itensPedido() == null || dto.itensPedido().isEmpty()) {
            throw new PedidoException("Pedido deve conter pelo menos um item");
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setEnderecoEntrega(enderecoEntrega);
        pedido.setEnderecoOrigem(enderecoOrigem);
        pedido.setFormaPagamento(dto.formaPagamento());
        pedido.setDsObservacoes(dto.dsObservacoes());
        pedido.setVlEntrega(calcularEntrega(enderecoOrigem, enderecoEntrega));
        pedido.setTempoEstimadoEntrega(restaurante.getTempoMediaEntrega());

        if (dto.cupomId() != null) {
            Cupom cupom = cupomRepository.findById(dto.cupomId())
                    .orElseThrow(() -> new CupomNaoEncontradoException(dto.cupomId()));
            pedido.setCupom(cupom);
        }

        pedido = pedidoRepository.save(pedido);
        log.debug("Pedido salvo inicialmente com ID: {}", pedido.getIdPedido());

        BigDecimal subtotal = processarItensPedido(pedido, dto.itensPedido());

        pedido.setVlSubtotal(subtotal);

        BigDecimal desconto = BigDecimal.ZERO;
        if (pedido.getCupom() != null) {
            desconto = calcularDescontoCupom(pedido.getCupom(), subtotal);
            pedido.setVlDesconto(desconto);
        } else {
            pedido.setVlDesconto(BigDecimal.ZERO);
        }

        BigDecimal total = subtotal.add(pedido.getVlEntrega()).subtract(desconto);
        pedido.setVlTotal(total.max(BigDecimal.ZERO));

        pedido = pedidoRepository.save(pedido);

        try {
            log.info("Iniciando criação automática da entrega para o pedido {}", pedido.getIdPedido());

            EntregaRequestDTO entregaDTO = new EntregaRequestDTO(
                    pedido.getIdPedido(),
                    pedido.getTempoEstimadoEntrega(),
                    pedido.getEnderecoOrigem().getIdEndereco(),
                    pedido.getEnderecoEntrega().getIdEndereco()
            );

            entregaService.cadastrarEntrega(entregaDTO);

        } catch (Exception e) {
            log.error("Erro ao criar entrega automática para o pedido {}: {}", pedido.getIdPedido(), e.getMessage());
        }

        log.info("Pedido criado com sucesso. ID: {}, Total: {}", pedido.getIdPedido(), pedido.getVlTotal());
        return new PedidoResponseDTO(pedido);
    }

    @Transactional
    public PedidoResponseDTO aceitarPedido(Long idPedido, AceitarPedidoRequestDTO dto) {
        log.info("Tentativa de aceitar/recusar pedido ID: {}", idPedido);
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> {
                    log.error("Pedido não encontrado: {}", idPedido);
                    return new PedidoNaoEncontradoException(idPedido);
                });

        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new PedidoException("Apenas pedidos pendentes podem ser aceitos ou recusados");
        }

        if (pedido.isAceito()) {
            throw new PedidoException("Este pedido já foi aceito anteriormente");
        }

        if (dto.aceitar()) {
            log.info("Pedido {} aceite pelo restaurante.", idPedido);
            pedido.setAceito(true);
            pedido.setDtAceitacao(LocalDateTime.now());
            pedido.setStatus(StatusPedido.CONFIRMADO);
            pedido.setDtAtualizacao(LocalDateTime.now());

            emailService.enviarEmailConfirmacaoPedido(
                    pedido.getCliente().getEmailCliente(),
                    pedido.getIdPedido(),
                    pedido.getRestaurante().getNmRestaurante()
            ).exceptionally(ex -> {
                log.error("Falha ao enviar email de confirmação: " + ex.getMessage());
                return false;
            });

        } else {
            if (dto.motivoRecusa() == null || dto.motivoRecusa().isBlank()) {
                throw new PedidoException("Motivo de recusa é obrigatório");
            }
            log.warn("Pedido {} recusado pelo restaurante. Motivo: {}", idPedido, dto.motivoRecusa());
            pedido.setAceito(false);
            pedido.setMotivoRecusa(dto.motivoRecusa());
            pedido.setStatus(StatusPedido.CANCELADO);
            pedido.setDtAtualizacao(LocalDateTime.now());

            emailService.enviarEmailPedidoCancelado(
                    pedido.getCliente().getEmailCliente(),
                    pedido.getIdPedido(),
                    dto.motivoRecusa()
            );
        }

        pedido = pedidoRepository.save(pedido);
        return new PedidoResponseDTO(pedido);
    }

    @Transactional
    public PedidoResponseDTO recusarPedido(Long idPedido, String motivo) {
        if (motivo == null || motivo.isBlank()) {
            throw new PedidoException("Motivo de recusa é obrigatório");
        }

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(idPedido));

        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new PedidoException("Apenas pedidos pendentes podem ser recusados");
        }

        pedido.setAceito(false);
        pedido.setMotivoRecusa(motivo);
        pedido.setStatus(StatusPedido.CANCELADO);
        pedido.setDtAtualizacao(LocalDateTime.now());

        pedido = pedidoRepository.save(pedido);
        return new PedidoResponseDTO(pedido);
    }

    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .map(PedidoResponseDTO::new)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarPorCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new ClienteNaoEncontradoException(clienteId);
        }
        return pedidoRepository.findByCliente_Id(clienteId).stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarPorRestaurante(Long restauranteId) {
        if (!restauranteRepository.existsById(restauranteId)) {
            throw new RestauranteNaoEncontradoException(restauranteId);
        }
        return pedidoRepository.findByRestaurante_Id(restauranteId).stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarPedidosPendentes(Long restauranteId) {
        if (!restauranteRepository.existsById(restauranteId)) {
            throw new RestauranteNaoEncontradoException(restauranteId);
        }
        return pedidoRepository.findByRestaurante_IdAndStatus(restauranteId, StatusPedido.PENDENTE)
                .stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarPedidosAceitos(Long restauranteId) {
        if (!restauranteRepository.existsById(restauranteId)) {
            throw new RestauranteNaoEncontradoException(restauranteId);
        }
        return pedidoRepository.findPedidosAceitosByRestaurante(restauranteId).stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarPedidosRecusados(Long restauranteId) {
        if (!restauranteRepository.existsById(restauranteId)) {
            throw new RestauranteNaoEncontradoException(restauranteId);
        }
        return pedidoRepository.findPedidosRecusadosByRestaurante(restauranteId).stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PedidoResponseDTO atualizarStatus(Long id, StatusPedido novoStatus) {
        log.info("A atualizar status do pedido {} para {}", id, novoStatus);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));

        if (!pedido.isAceito() && novoStatus != StatusPedido.CANCELADO) {
            throw new PedidoException("Apenas pedidos aceitos podem ter o status atualizado");
        }

        validarTransicaoStatus(pedido.getStatus(), novoStatus);

        pedido.setStatus(novoStatus);
        pedido.setDtAtualizacao(LocalDateTime.now());

        pedido = pedidoRepository.save(pedido);
        log.info("Status do pedido {} atualizado com sucesso.", id);
        return new PedidoResponseDTO(pedido);
    }

    @Transactional
    public void cancelarPedido(Long id, String motivo) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));

        if (pedido.getStatus() != StatusPedido.PENDENTE &&
                pedido.getStatus() != StatusPedido.CONFIRMADO) {
            throw new PedidoException("O pedido não pode ser cancelado neste status");
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        pedido.setMotivoRecusa(motivo);
        pedido.setDtAtualizacao(LocalDateTime.now());
        pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularSubtotal(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
        return pedido.getVlSubtotal();
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularTotal(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
        return pedido.getVlTotal();
    }

    @Transactional(readOnly = true)
    public Integer calcularTempoEstimado(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
        return pedido.getTempoEstimadoEntrega();
    }

    @Transactional
    public Boolean finalizarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));

        if (pedido.getStatus() != StatusPedido.SAIU_PARA_ENTREGA) {
            throw new PedidoException("Apenas pedidos em rota podem ser finalizados");
        }

        pedido.setStatus(StatusPedido.ENTREGUE);
        pedido.setDtAtualizacao(LocalDateTime.now());
        pedidoRepository.save(pedido);
        return true;
    }

    @Transactional
    public PedidoResponseDTO aplicarCupom(Long idPedido, Long cupomId) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(idPedido));

        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new PedidoException("Cupom só pode ser aplicado em pedidos pendentes");
        }

        Cupom cupom = cupomRepository.findById(cupomId)
                .orElseThrow(() -> new CupomNaoEncontradoException(cupomId));

        if (!cupom.getRestaurante().getId().equals(pedido.getRestaurante().getId())) {
            throw new CupomInvalidoException("Cupom não pertence a este restaurante");
        }

        validarCupom(cupom, pedido.getVlSubtotal());

        pedido.setCupom(cupom);
        BigDecimal desconto = calcularDescontoCupom(cupom, pedido.getVlSubtotal());
        pedido.setVlDesconto(desconto);

        BigDecimal total = pedido.getVlSubtotal()
                .add(pedido.getVlEntrega())
                .subtract(desconto);
        pedido.setVlTotal(total.max(BigDecimal.ZERO));

        pedido.setDtAtualizacao(LocalDateTime.now());
        pedido = pedidoRepository.save(pedido);

        return new PedidoResponseDTO(pedido);
    }

    @Transactional
    public PedidoResponseDTO removerCupom(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(idPedido));

        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new PedidoException("Cupom só pode ser removido de pedidos pendentes");
        }

        pedido.setCupom(null);
        pedido.setVlDesconto(BigDecimal.ZERO);

        BigDecimal total = pedido.getVlSubtotal().add(pedido.getVlEntrega());
        pedido.setVlTotal(total);

        pedido.setDtAtualizacao(LocalDateTime.now());
        pedido = pedidoRepository.save(pedido);

        return new PedidoResponseDTO(pedido);
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularDesconto(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(idPedido));
        return pedido.getVlDesconto() != null ? pedido.getVlDesconto() : BigDecimal.ZERO;
    }


    private BigDecimal processarItensPedido(Pedido pedido, List<ItemPedidoRequestDTO> itensDTO) {
        BigDecimal subtotal = BigDecimal.ZERO;

        for (ItemPedidoRequestDTO itemDTO : itensDTO) {
            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() -> new ProdutoNaoEncontradoException(itemDTO.produtoId()));

            if (!produto.isAtivo()) {
                throw new RegraDeNegocioException("Produto " + produto.getNmProduto() + " não está disponível");
            }

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQtQuantidade(itemDTO.qtQuantidade());
            item.setVlPrecoUnitario(produto.getVlPreco());
            item.setDsObservacao(itemDTO.dsObservacao());

            BigDecimal subtotalItem = produto.getVlPreco().multiply(BigDecimal.valueOf(itemDTO.qtQuantidade()));

            if (itemDTO.adicionaisIds() != null && !itemDTO.adicionaisIds().isEmpty()) {
                List<Adicional> adicionais = adicionalRepository.findAllById(itemDTO.adicionaisIds());

                for (Adicional adicional : adicionais) {
                    if (!adicional.isDisponivel()) {
                        throw new RegraDeNegocioException("Adicional " + adicional.getNmAdicional() + " não está disponível");
                    }
                }

                item.setAdicionais(adicionais);

                BigDecimal somaAdicionais = adicionais.stream()
                        .map(Adicional::getVlPrecoAdicional)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal valorTotalAdicionais = somaAdicionais.multiply(BigDecimal.valueOf(itemDTO.qtQuantidade()));

                subtotalItem = subtotalItem.add(valorTotalAdicionais);
            }

            item.setVlSubtotal(subtotalItem);
            itemPedidoRepository.save(item);

            subtotal = subtotal.add(subtotalItem);
        }

        return subtotal;
    }

    private void validarRestauranteDisponivel(Restaurante restaurante) {
        if (!restaurante.isAberto()) {
            throw new RegraDeNegocioException("Restaurante está fechado no momento");
        }
        if (!restaurante.isDisponivel()) {
            throw new RegraDeNegocioException("Restaurante indisponível para pedidos");
        }
    }

    private void validarEnderecosPertencem(Endereco enderecoEntrega, Endereco enderecoOrigem,
                                           Long clienteId, Long restauranteId) {
        if (enderecoEntrega.getCliente() == null ||
                !enderecoEntrega.getCliente().getId().equals(clienteId)) {
            throw new PedidoException("O endereço de entrega não pertence ao cliente informado");
        }

        if (enderecoOrigem.getRestaurante() == null ||
                !enderecoOrigem.getRestaurante().getId().equals(restauranteId)) {
            throw new PedidoException("O endereço de origem não pertence ao restaurante informado");
        }
    }

    private void validarCupom(Cupom cupom, BigDecimal valorPedido) {
        if (!cupom.isAtivo()) {
            throw new CupomInvalidoException("Cupom inativo");
        }

        if (cupom.getDtValidade().isBefore(LocalDateTime.now())) {
            throw new CupomInvalidoException("Cupom expirado");
        }

        if (cupom.getQtdUsoMaximo() != null && cupom.getQtdUsado() >= cupom.getQtdUsoMaximo()) {
            throw new CupomInvalidoException("Cupom atingiu o limite de uso");
        }

        if (cupom.getVlMinimoPedido() != null && valorPedido.compareTo(cupom.getVlMinimoPedido()) < 0) {
            throw new CupomInvalidoException("Valor mínimo do pedido não atingido");
        }
    }

    private BigDecimal calcularDescontoCupom(Cupom cupom, BigDecimal valorPedido) {
        BigDecimal desconto;
        switch (cupom.getTipoCupom()) {
            case VALOR_FIXO -> desconto = cupom.getVlDesconto();
            case PERCENTUAL -> {
                desconto = valorPedido.multiply(cupom.getPercentualDesconto())
                        .divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN);
            }
            default -> desconto = BigDecimal.ZERO;
        }

        return desconto.min(valorPedido);
    }

    private BigDecimal calcularEntrega(Endereco origem, Endereco destino) {
        try {
            if (origem.getLatitude() == null || origem.getLongitude() == null ||
                    destino.getLatitude() == null || destino.getLongitude() == null) {
                return new BigDecimal("5.00");
            }

            double distanciaKm = DistanciaUtils.calcularDistancia(
                    origem.getLatitude(),
                    origem.getLongitude(),
                    destino.getLatitude(),
                    destino.getLongitude()
            );

            if (distanciaKm > 50.0) {
                throw new RegraDeNegocioException(
                        String.format("Distância de %.2f km excede o limite de entrega (50 km)", distanciaKm)
                );
            }

            return FreteUtils.calcularFrete(distanciaKm);

        } catch (IllegalArgumentException e) {
            throw new RegraDeNegocioException("Erro ao calcular frete: " + e.getMessage());
        }

    }

    private void validarTransicaoStatus(StatusPedido statusAtual, StatusPedido novoStatus) {
        StatusPedidoValidator.validarTransicao(statusAtual, novoStatus);
    }


}
