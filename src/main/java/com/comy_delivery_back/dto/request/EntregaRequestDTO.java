package com.comy_delivery_back.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Dados para criação de uma entrega")
public record EntregaRequestDTO(
        @Schema(description = "ID do pedido", example = "1", required = true)
        @NotNull
        Long pedidoId,

        @Schema(description = "Tempo estimado de entrega em minutos", example = "45")
        Integer tempoEstimadoMinutos,

        @Schema(description = "ID do endereço de origem (restaurante)", example = "1")
        Long enderecoOrigem,

        @Schema(description = "ID do endereço de destino (cliente)", example = "2")
        Long enderecoDestino,

        @Schema(description = "Valor da entrega", example = "5.00")
        BigDecimal vlEntrega
) {}
