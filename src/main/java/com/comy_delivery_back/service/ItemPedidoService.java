package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.ItemPedidoRequestDTO;
import com.comy_delivery_back.dto.response.ItemPedidoResponseDTO;
import com.comy_delivery_back.enums.StatusPedido;
import com.comy_delivery_back.exception.*;
import com.comy_delivery_back.model.Adicional;
import com.comy_delivery_back.model.ItemPedido;
import com.comy_delivery_back.model.Pedido;
import com.comy_delivery_back.model.Produto;
import com.comy_delivery_back.repository.AdicionalRepository;
import com.comy_delivery_back.repository.ItemPedidoRepository;
import com.comy_delivery_back.repository.PedidoRepository;
import com.comy_delivery_back.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final AdicionalRepository adicionalRepository;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository,
                             PedidoRepository pedidoRepository,
                             ProdutoRepository produtoRepository,
                             AdicionalRepository adicionalRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.adicionalRepository = adicionalRepository;
    }

    @Transactional
    public ItemPedidoResponseDTO adicionarItem(ItemPedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.pedidoId())
                .orElseThrow(() -> new PedidoNaoEncontradoException(dto.pedidoId()));

        validarPedidoPodeSerModificado(pedido);

        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new ProdutoNaoEncontradoException(dto.produtoId()));

        if (!produto.isAtivo()) {
            throw new RegraDeNegocioException("Produto não está disponível");
        }

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQtQuantidade(dto.qtQuantidade());
        item.setVlPrecoUnitario(produto.getVlPreco());
        item.setDsObservacao(dto.dsObservacao());

        double subtotal = produto.getVlPreco() * dto.qtQuantidade();

        if (dto.adicionaisIds() != null && !dto.adicionaisIds().isEmpty()) {
            List<Adicional> adicionais = adicionalRepository.findAllById(dto.adicionaisIds());

            if (adicionais.size() != dto.adicionaisIds().size()) {
                throw new AdicionalNaoEncontradoException(0L);
            }

            for (Adicional adicional : adicionais) {
                if (!adicional.isDisponivel()) {
                    throw new RegraDeNegocioException("Adicional " + adicional.getNmAdicional() + " não está disponível");
                }
            }

            item.setAdicionais(adicionais);

            double valorAdicionais = adicionais.stream()
                    .mapToDouble(Adicional::getVlPrecoAdicional)
                    .sum() * dto.qtQuantidade();

            subtotal += valorAdicionais;
        }

        item.setVlSubtotal(subtotal);

        ItemPedido itemSalvo = itemPedidoRepository.save(item);

        atualizarValoresPedido(pedido);

        return new ItemPedidoResponseDTO(itemSalvo);
    }

    @Transactional(readOnly = true)
    public ItemPedidoResponseDTO buscarPorId(Long id) {
        return itemPedidoRepository.findById(id)
                .map(ItemPedidoResponseDTO::new)
                .orElseThrow(() -> new ItemPedidoNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
    public List<ItemPedidoResponseDTO> listarPorPedido(Long pedidoId) {
        if (!pedidoRepository.existsById(pedidoId)) {
            throw new PedidoNaoEncontradoException(pedidoId);
        }

        return itemPedidoRepository.findByPedido_IdPedido(pedidoId).stream()
                .map(ItemPedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemPedidoResponseDTO atualizarQuantidade(Long id, Integer novaQuantidade) {
        if (novaQuantidade == null || novaQuantidade <= 0) {
            throw new RegraDeNegocioException("Quantidade deve ser maior que zero");
        }

        ItemPedido item = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNaoEncontradoException(id));

        validarPedidoPodeSerModificado(item.getPedido());

        item.setQtQuantidade(novaQuantidade);

        double subtotal = item.getVlPrecoUnitario() * novaQuantidade;

        if (item.getAdicionais() != null && !item.getAdicionais().isEmpty()) {
            double valorAdicionais = item.getAdicionais().stream()
                    .mapToDouble(Adicional::getVlPrecoAdicional)
                    .sum() * novaQuantidade;
            subtotal += valorAdicionais;
        }

        item.setVlSubtotal(subtotal);

        ItemPedido itemAtualizado = itemPedidoRepository.save(item);

        atualizarValoresPedido(item.getPedido());

        return new ItemPedidoResponseDTO(itemAtualizado);
    }

    @Transactional
    public ItemPedidoResponseDTO atualizarObservacao(Long id, String observacao) {
        ItemPedido item = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNaoEncontradoException(id));

        validarPedidoPodeSerModificado(item.getPedido());

        if (observacao != null && observacao.length() > 300) {
            throw new RegraDeNegocioException("Observação não pode ter mais de 300 caracteres");
        }

        item.setDsObservacao(observacao);
        ItemPedido itemAtualizado = itemPedidoRepository.save(item);

        return new ItemPedidoResponseDTO(itemAtualizado);
    }

    @Transactional
    public ItemPedidoResponseDTO adicionarAdicionais(Long id, List<Long> adicionaisIds) {
        ItemPedido item = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNaoEncontradoException(id));

        validarPedidoPodeSerModificado(item.getPedido());

        List<Adicional> novosAdicionais = adicionalRepository.findAllById(adicionaisIds);

        if (novosAdicionais.size() != adicionaisIds.size()) {
            throw new AdicionalNaoEncontradoException(0L);
        }

        for (Adicional adicional : novosAdicionais) {
            if (!adicional.isDisponivel()) {
                throw new RegraDeNegocioException("Adicional " + adicional.getNmAdicional() + " não está disponível");
            }
        }

        List<Adicional> adicionaisAtuais = item.getAdicionais();
        if (adicionaisAtuais == null) {
            adicionaisAtuais = new ArrayList<>();
        }

        for (Adicional adicional : novosAdicionais) {
            if (!adicionaisAtuais.contains(adicional)) {
                adicionaisAtuais.add(adicional);
            }
        }

        item.setAdicionais(adicionaisAtuais);

        recalcularSubtotal(item);

        ItemPedido itemAtualizado = itemPedidoRepository.save(item);

        atualizarValoresPedido(item.getPedido());

        return new ItemPedidoResponseDTO(itemAtualizado);
    }

    @Transactional
    public ItemPedidoResponseDTO removerAdicionais(Long id, List<Long> adicionaisIds) {
        ItemPedido item = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNaoEncontradoException(id));

        validarPedidoPodeSerModificado(item.getPedido());

        List<Adicional> adicionaisAtuais = item.getAdicionais();
        if (adicionaisAtuais != null && !adicionaisAtuais.isEmpty()) {
            adicionaisAtuais.removeIf(adicional -> adicionaisIds.contains(adicional.getIdAdicional()));
            item.setAdicionais(adicionaisAtuais);
        }

        recalcularSubtotal(item);

        ItemPedido itemAtualizado = itemPedidoRepository.save(item);

        atualizarValoresPedido(item.getPedido());

        return new ItemPedidoResponseDTO(itemAtualizado);
    }

    @Transactional(readOnly = true)
    public Double calcularSubtotalItem(Long id) {
        ItemPedido item = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNaoEncontradoException(id));

        return item.getVlSubtotal();
    }

    @Transactional
    public void removerItem(Long id) {
        ItemPedido item = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNaoEncontradoException(id));

        validarPedidoPodeSerModificado(item.getPedido());

        Pedido pedido = item.getPedido();

        itemPedidoRepository.delete(item);

        atualizarValoresPedido(pedido);
    }

    @Transactional
    public ItemPedidoResponseDTO duplicarItem(Long id) {
        ItemPedido itemOriginal = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNaoEncontradoException(id));

        validarPedidoPodeSerModificado(itemOriginal.getPedido());

        ItemPedido novoItem = new ItemPedido();
        novoItem.setPedido(itemOriginal.getPedido());
        novoItem.setProduto(itemOriginal.getProduto());
        novoItem.setQtQuantidade(itemOriginal.getQtQuantidade());
        novoItem.setVlPrecoUnitario(itemOriginal.getVlPrecoUnitario());
        novoItem.setVlSubtotal(itemOriginal.getVlSubtotal());
        novoItem.setDsObservacao(itemOriginal.getDsObservacao());

        if (itemOriginal.getAdicionais() != null && !itemOriginal.getAdicionais().isEmpty()) {
            novoItem.setAdicionais(new ArrayList<>(itemOriginal.getAdicionais()));
        }

        ItemPedido itemDuplicado = itemPedidoRepository.save(novoItem);

        atualizarValoresPedido(itemOriginal.getPedido());

        return new ItemPedidoResponseDTO(itemDuplicado);
    }

    // ========== MÉTODOS AUXILIARES ==========

    private void validarPedidoPodeSerModificado(Pedido pedido) {
        if (pedido.getStatus() != StatusPedido.PENDENTE && pedido.getStatus() != StatusPedido.CONFIRMADO) {
            throw new PedidoException("Pedido não pode ser modificado no status atual: " + pedido.getStatus());
        }

        if (!pedido.isAceito() && pedido.getMotivoRecusa() != null) {
            throw new PedidoException("Pedido já foi recusado e não pode ser modificado");
        }
    }

    private void recalcularSubtotal(ItemPedido item) {
        double subtotal = item.getVlPrecoUnitario() * item.getQtQuantidade();

        if (item.getAdicionais() != null && !item.getAdicionais().isEmpty()) {
            double valorAdicionais = item.getAdicionais().stream()
                    .mapToDouble(Adicional::getVlPrecoAdicional)
                    .sum() * item.getQtQuantidade();
            subtotal += valorAdicionais;
        }

        item.setVlSubtotal(subtotal);
    }

    private void atualizarValoresPedido(Pedido pedido) {
        List<ItemPedido> itens = itemPedidoRepository.findByPedido_IdPedido(pedido.getIdPedido());

        Double subtotal = itens.stream()
                .mapToDouble(ItemPedido::getVlSubtotal)
                .sum();

        pedido.setVlSubtotal(subtotal);

        double desconto = pedido.getVlDesconto() != null ? pedido.getVlDesconto() : 0.0;
        Double frete = pedido.getVlFrete() != null ? pedido.getVlFrete() : 0.0;

        double total = subtotal + frete - desconto;
        pedido.setVlTotal(Math.max(total, 0.0));

        pedidoRepository.save(pedido);
    }
}
