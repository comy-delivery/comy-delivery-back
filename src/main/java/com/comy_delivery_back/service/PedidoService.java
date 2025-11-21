package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.AceitarPedidoRequestDTO;
import com.comy_delivery_back.dto.request.ItemPedidoRequestDTO;
import com.comy_delivery_back.dto.request.PedidoRequestDTO;
import com.comy_delivery_back.dto.response.PedidoResponseDTO;
import com.comy_delivery_back.enums.StatusPedido;
import com.comy_delivery_back.exception.*;
import com.comy_delivery_back.model.*;
import com.comy_delivery_back.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, RestauranteRepository restauranteRepository, EnderecoRepository enderecoRepository, ProdutoRepository produtoRepository, CupomRepository cupomRepository, ItemPedidoRepository itemPedidoRepository, AdicionalRepository adicionalRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
        this.enderecoRepository = enderecoRepository;
        this.produtoRepository = produtoRepository;
        this.cupomRepository = cupomRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.adicionalRepository = adicionalRepository;
    }


    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.cliente())
                .orElseThrow(() -> new ClienteNaoEncontradoException(dto.cliente()));

        Restaurante restaurante = restauranteRepository.findById(dto.restaurante())
                .orElseThrow(() -> new RestauranteNaoEncontradoException(dto.restaurante()));

        Endereco enderecoEntrega = enderecoRepository.findById(dto.enderecoEntregaId())
                .orElseThrow(() -> new EnderecoNaoEncontradoException(dto.enderecoEntregaId()));

        Endereco enderecoOrigem = enderecoRepository.findById(dto.enderecoOrigemId())
                .orElseThrow(() -> new EnderecoNaoEncontradoException(dto.enderecoOrigemId()));

        if (!enderecoEntrega.getCliente().getId().equals(dto.cliente())) {
            throw new PedidoException("O endereço de entrega não pertence ao cliente informado");
        }

        if (!enderecoOrigem.getRestaurante().getId().equals(dto.restaurante())) {
            throw new PedidoException("O endereço de origem não pertence ao restaurante informado");
        }

        Pedido pedido = new Pedido();
        BeanUtils.copyProperties(dto, pedido);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setEnderecoEntrega(enderecoEntrega);
        pedido.setEnderecoOrigem(enderecoOrigem);
        pedido.setFormaPagamento(dto.formaPagamento());
        pedido.setDsObservacoes(dto.dsObservacoes());

        if (dto.cupomId() != null) {
            Cupom cupom = cupomRepository.findById(dto.cupomId())
                    .orElseThrow(() -> new CupomNaoEncontradoException(dto.cupomId()));
            pedido.setCupom(cupom);
        }

        pedido = pedidoRepository.save(pedido);

        Double subtotal = 0.00;

        for (ItemPedidoRequestDTO itemDTO : dto.itensPedido()) {
            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() -> new ProdutoNaoEncontradoException(itemDTO.produtoId()));

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQtQuantidade(itemDTO.qtQuantidade());
            item.setVlPrecoUnitario(produto.getVlPreco());
            item.setDsObservacao(itemDTO.dsObservacao());

            Double subtotalItem = produto.getVlPreco() * itemDTO.qtQuantidade();

            if (itemDTO.adicionaisIds() != null && !itemDTO.adicionaisIds().isEmpty()) {
                List<Adicional> adicionais = adicionalRepository.findAllById(itemDTO.adicionaisIds());
                item.setAdicionais(adicionais);

                Double valorTotalAdicionais = adicionais.stream()
                        .mapToDouble(Adicional::getVlPrecoAdicional)
                        .sum();

                Double valorAdicionaisComQuantidade = valorTotalAdicionais * itemDTO.qtQuantidade();
                subtotalItem = subtotalItem + valorAdicionaisComQuantidade;
            }

            item.setVlSubtotal(subtotalItem);
            subtotal = subtotal + subtotalItem;

            itemPedidoRepository.save(item);
        }

        pedido.setVlSubtotal(subtotal);

        Double desconto = 0.00;
        if (pedido.getCupom() != null) {
            desconto = calcularDesconto(pedido.getCupom(), subtotal);
        }
        pedido.setVlDesconto(desconto);

        Double total = subtotal + pedido.getVlFrete() - desconto;
        pedido.setVlTotal(total);
        pedido.setTempoEstimadoEntrega(restaurante.getTempoMediaEntrega());

        pedido = pedidoRepository.save(pedido);

        return new PedidoResponseDTO(pedido);
    }

    @Transactional
    public PedidoResponseDTO aceitarPedido(Long idPedido, AceitarPedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(idPedido));

        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new PedidoException("Apenas pedidos pendentes podem ser aceitos ou recusados");
        }

        if (pedido.isAceito()) {
            throw new PedidoException("Este pedido já foi aceito anteriormente");
        }

        if (dto.aceitar()) {
            pedido.setAceito(true);
            pedido.setDtAceitacao(LocalDateTime.now());
            pedido.setStatus(StatusPedido.CONFIRMADO);
            pedido.setDtAtualizacao(LocalDateTime.now());
        } else {
            if (dto.motivoRecusa() == null || dto.motivoRecusa().isBlank()) {
                throw new PedidoException("Motivo de recusa é obrigatório");
            }
            pedido.setAceito(false);
            pedido.setMotivoRecusa(dto.motivoRecusa());
            pedido.setStatus(StatusPedido.CANCELADO);
            pedido.setDtAtualizacao(LocalDateTime.now());
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

    public PedidoResponseDTO buscarPorId(Long id) {
        return this.pedidoRepository.findById(id).map(PedidoResponseDTO::new)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));

    }

    public List<PedidoResponseDTO> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByCliente_Id(clienteId).stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> listarPorRestaurante(Long restauranteId) {
        return pedidoRepository.findByRestaurante_Id(restauranteId).stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> listarPedidosPendentes(Long restauranteId) {
        return pedidoRepository.findByRestaurante_IdAndStatus(restauranteId, StatusPedido.PENDENTE)
                .stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> listarPedidosAceitos(Long restauranteId) {
        List<Pedido> pedidos = pedidoRepository.findByRestaurante_Id(restauranteId);
        return pedidos.stream()
                .filter(Pedido::isAceito)
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> listarPedidosRecusados(Long restauranteId) {
        List<Pedido> pedidos = pedidoRepository.findByRestaurante_Id(restauranteId);
        return pedidos.stream()
                .filter(p -> !p.isAceito() && p.getMotivoRecusa() != null)
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PedidoResponseDTO atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));

        if (!pedido.isAceito() && novoStatus != StatusPedido.CANCELADO) {
            throw new PedidoException("Apenas pedidos aceitos podem ter o status atualizado");
        }

        pedido.setStatus(novoStatus);
        pedido.setDtAtualizacao(LocalDateTime.now());

        pedido = pedidoRepository.save(pedido);
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
        pedido.setDtAtualizacao(LocalDateTime.now());
        pedidoRepository.save(pedido);
    }

    public Double calcularSubtotal(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
        return pedido.getVlSubtotal();
    }

    public Double calcularTotal(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
        return pedido.getVlTotal();
    }

    public Integer calcularTempoEstimado(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
        return pedido.getTempoEstimadoEntrega();
    }

    @Transactional
    public Boolean finalizarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
        pedido.setStatus(StatusPedido.ENTREGUE);
        pedido.setDtAtualizacao(LocalDateTime.now());
        pedidoRepository.save(pedido);
        return true;
    }

    private Double calcularDesconto(Cupom cupom, Double valorPedido) {
        if (!cupom.isAtivo()) {
            throw new CupomInvalidoException("Cupom inativo");
        }

        if (cupom.getDtValidade().isBefore(LocalDateTime.now())) {
            throw new CupomInvalidoException("Cupom expirado");
        }

        if (cupom.getVlMinimoPedido() != null &&
                valorPedido.compareTo(cupom.getVlMinimoPedido()) < 0) {
            throw new CupomInvalidoException("Valor mínimo não atingido");
        }

        return switch (cupom.getTipoCupom()) {
            case VALOR_FIXO -> cupom.getVlDesconto();
            case PERCENTUAL -> valorPedido * (cupom.getPercentualDesconto() / 100);
            default -> 0.00;
        };
    }

}
