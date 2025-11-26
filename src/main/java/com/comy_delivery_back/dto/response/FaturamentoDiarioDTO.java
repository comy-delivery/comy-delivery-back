package com.comy_delivery_back.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Faturamento de um dia específico")
public record FaturamentoDiarioDTO(
        @Schema(description = "Data do faturamento", example = "2024-10-10")
        LocalDate data,

        @Schema(description = "Valor total faturado no dia", example = "500.00")
        BigDecimal faturamentoTotal
) {}
