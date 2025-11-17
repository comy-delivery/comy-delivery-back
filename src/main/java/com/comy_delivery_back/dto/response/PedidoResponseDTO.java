package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.enums.FormaPagamento;
import com.comy_delivery_back.enums.StatusPedido;
import com.comy_delivery_back.model.Pedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record PedidoResponseDTO(
        Long idPedido,
        Long clienteId,
        RestauranteResponseDTO restaurante,
        EnderecoResponseDTO endereco,
        CupomResponseDTO cupom,
        List<ItemPedidoResponseDTO> itensPedido,
        LocalDateTime dtCriacao,
        LocalDateTime dtAtualizacao,
        Double vlSubtotal,
        Double vlFrete,
        Double vlDesconto,
        Double vlTotal,
        StatusPedido status,
        FormaPagamento formaPagamento,
        Integer tempoEstimadoEntrega,
        String dsObservacoes

) {

    public  PedidoResponseDTO(Pedido p){
        this(
                p.getIdPedido(),
                p.getCliente().getId(),
                new RestauranteResponseDTO(p.getRestaurante()),
                new EnderecoResponseDTO(p.getEndereco()),
                new CupomResponseDTO(p.getCupom()),
                p.getItensPedido().stream().map(ItemPedidoResponseDTO::new).collect(Collectors.toList()),
                p.getDtCriacao(),
                p.getDtAtualizacao(),
                p.getVlSubtotal(),
                p.getVlFrete(),
                p.getVlDesconto(),
                p.getVlTotal(),
                p.getStatus(),
                p.getFormaPagamento(),
                p.getTempoEstimadoEntrega(),
                p.getDsObservacoes()
        );
    }
}
