package com.comy_delivery_back.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Métricas de desempenho do entregador")
public record DashboardEntregadorDTO(
        @Schema(description = "Quantidade total de entregas concluídas em todo o histórico", example = "150")
        Long quantidadeTotalEntregas,

        @Schema(description = "Valor total ganho com entregas", example = "1500.50")
        BigDecimal valorTotalRecebido,

        @Schema(description = "Data de referência", example = "2025-11-25")
        LocalDate dataReferencia
) {
}
