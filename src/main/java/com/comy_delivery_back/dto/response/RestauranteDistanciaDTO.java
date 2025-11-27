package com.comy_delivery_back.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Restaurante com informações de distância e estimativas")
public record RestauranteDistanciaDTO(
        @Schema(description = "Dados completos do restaurante")
        RestauranteResponseDTO restaurante,

        @Schema(description = "Distância em quilômetros do cliente", example = "2.5")
        Double distanciaKm,

        @Schema(description = "Valor estimado do frete", example = "5.25")
        BigDecimal valorFreteEstimado,

        @Schema(description = "Tempo estimado de entrega em minutos", example = "45")
        Integer tempoEstimadoEntrega,

        @Schema(description = "Preço médio dos produtos do restaurante", example = "35.00")
        BigDecimal mediaPrecoProdutos
) {}
