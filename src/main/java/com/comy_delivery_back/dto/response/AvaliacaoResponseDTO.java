package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.model.Avaliacao;

import java.time.LocalDateTime;

public record AvaliacaoResponseDTO(
        Long idAvaliacao,
        Long restauranteId,
        Long clienteId,
        Long pedidoId,
        Long entregadorId,
        Integer nuNota,
        String dsComentario,
        LocalDateTime dtAvaliacao,
        Integer avaliacaoEntrega

) {

    public AvaliacaoResponseDTO(Avaliacao a){
        this(
                a.getIdAvaliacao(),
                a.getRestaurante().getId(),
                a.getCliente().getId(),
                a.getPedido().getIdPedido(),
                a.getEntregador().getId(),
                a.getNuNota(),
                a.getDsComentario(),
                a.getDtAvaliacao(),
                a.getAvaliacaoEntrega()
        );
    }
}
