package com.comy_delivery_back.service;

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
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Restaurante restaurante = restauranteRepository.findById(dto.restaurante())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        Endereco endereco = enderecoRepository.findById(dto.endereco())
                .orElseThrow(() -> new EnderecoNaoEncontradoException(dto.endereco()));

        Pedido pedido = new Pedido();
        BeanUtils.copyProperties(dto, pedido);

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
                    .orElseThrow(() -> new ProdutoNaoEncontradoException(itemDTO.produtoId())); // Corrigido para produtoId

            ItemPedido item = new ItemPedido();
            BeanUtils.copyProperties(itemDTO, item);
            item.setPedido(pedido);
            item.setProduto(produto);

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

    public PedidoResponseDTO buscarPorId(Long id) {
        return this.pedidoRepository.findById(id).map(PedidoResponseDTO::new)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));

    }

    public List<PedidoResponseDTO> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByCliente_IdCliente(clienteId).stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> listarPorRestaurante(Long restauranteId) {
        return pedidoRepository.findByRestaurante_IdRestaurante(restauranteId).stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PedidoResponseDTO atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));

        pedido.setStatus(novoStatus);


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
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setStatus(StatusPedido.ENTREGUE);
        pedido.setDtAtualizacao(LocalDateTime.now());
        pedidoRepository.save(pedido);
        return true;
    }

    private Double calcularDesconto(Cupom cupom, Double valorPedido) {
        if (!cupom.isAtivo()) {
            throw new RuntimeException("Cupom inativo");
        }

        if (cupom.getDtValidade().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Cupom expirado");
        }

        if (cupom.getVlMinimoPedido() != null &&
                valorPedido.compareTo(cupom.getVlMinimoPedido()) < 0) {
            throw new RuntimeException("Valor mínimo do pedido não atingido");
        }

        return switch (cupom.getTipoCupom()) {
            case VALOR_FIXO -> cupom.getVlDesconto();
            case PERCENTUAL -> valorPedido * (cupom.getPercentualDesconto() / 100);
            default -> 0.00;
        };

    }
}
