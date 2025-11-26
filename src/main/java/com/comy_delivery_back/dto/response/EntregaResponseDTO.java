package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.enums.StatusEntrega;
import com.comy_delivery_back.model.Entrega;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Resposta contendo dados de uma entrega")
public record EntregaResponseDTO(
        @Schema(description = "ID da entrega", example = "1")
        Long id,

        @Schema(description = "ID do pedido", example = "1")
        Long pedidoId,

        @Schema(description = "ID do entregador", example = "1")
        Long entregadorId,

        @Schema(description = "Status da entrega", example = "CONCLUIDA")
        StatusEntrega statusEntrega,

        @Schema(description = "ID do endereço de origem", example = "1")
        Long enderecoOrigemId,

        @Schema(description = "ID do endereço de destino", example = "2")
        Long enderecoDestinoId,

        @Schema(description = "Data e hora de início", example = "2024-10-10T19:10:00")
        LocalDateTime dataHoraInicio,

        @Schema(description = "Data e hora de conclusão", example = "2024-10-10T19:50:00")
        LocalDateTime dataHoraConclusao,

        @Schema(description = "Tempo estimado em minutos", example = "45")
        Integer tempoEstimadoMinutos,

        @Schema(description = "Valor da entrega", example = "5.00")
        BigDecimal valorEntrega,

        @Schema(description = "Avaliação do cliente (0-5)", example = "5.0")
        Double avaliacaoCliente
){
    public EntregaResponseDTO(Entrega entrega){
        this(
                entrega.getIdEntrega(),
                entrega.getPedido().getIdPedido(),
                entrega.getEntregador() != null ? entrega.getEntregador().getId() : null,
                entrega.getStatusEntrega(),
                entrega.getEnderecoOrigem() != null ? entrega.getEnderecoOrigem().getIdEndereco() : null,
                entrega.getEnderecoDestino() != null ? entrega.getEnderecoDestino().getIdEndereco() : null,
                entrega.getDataHoraInicio(),
                entrega.getDataHoraConclusao(),
                entrega.getTempoEstimadoMinutos(),
                entrega.getVlEntrega(),
                entrega.getAvaliacaoCliente()
        );
    }
}
