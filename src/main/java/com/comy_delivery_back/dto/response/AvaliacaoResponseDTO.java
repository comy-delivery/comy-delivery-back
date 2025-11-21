package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.model.Avaliacao;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AvaliacaoResponseDTO(
        Long idAvaliacao,

        RestauranteResponseDTO restauranteId,

        ClienteResponseDTO clienteId,

        PedidoResponseDTO pedidoId,

        EntregadorResponseDTO entregadorId,

        Integer nuNota,

        String dsComentario,

        LocalDateTime dtAvaliacao,

        Integer avaliacaoEntrega

) {

    public AvaliacaoResponseDTO(Avaliacao a){
        this(
                a.getIdAvaliacao(),
                new RestauranteResponseDTO(a.getRestaurante()),
                new ClienteResponseDTO(a.getCliente()),
                new PedidoResponseDTO(a.getPedido()),
                new EntregadorResponseDTO(a.getEntregador()),
                a.getNuNota(),
                a.getDsComentario(),
                a.getDtAvaliacao(),
                a.getAvaliacaoEntrega()
        );
    }
}
