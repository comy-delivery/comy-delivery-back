package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.model.Avaliacao;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Resposta contendo dados de uma avaliação")
public record AvaliacaoResponseDTO(
        @Schema(description = "ID da avaliação", example = "1")
        Long idAvaliacao,

        @Schema(description = "ID do restaurante", example = "1")
        Long restauranteId,

        @Schema(description = "ID do cliente", example = "1")
        Long clienteId,

        @Schema(description = "ID do pedido", example = "1")
        Long pedidoId,

        @Schema(description = "ID do entregador", example = "1")
        Long entregadorId,

        @Schema(description = "Nota (1-5)", example = "5")
        Integer nuNota,

        @Schema(description = "Comentário", example = "Excelente!")
        String dsComentario,

        @Schema(description = "Data e hora da avaliação", example = "2024-10-10T20:00:00")
        LocalDateTime dtAvaliacao,

        @Schema(description = "Nota da entrega (1-5)", example = "5")
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

