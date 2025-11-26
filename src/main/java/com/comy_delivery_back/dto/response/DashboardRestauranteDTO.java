package com.comy_delivery_back.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Métricas consolidadas do restaurante")
public record DashboardRestauranteDTO(
        @Schema(description = "Quantidade total de pedidos históricos", example = "1500")
        Long totalPedidosGeral,

        @Schema(description = "Histórico de faturamento agrupado por dia")
        List<FaturamentoDiarioDTO> historicoFaturamento
) {}
