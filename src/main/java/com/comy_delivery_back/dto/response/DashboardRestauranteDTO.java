package com.comy_delivery_back.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Métricas consolidadas do restaurante")
public record DashboardRestauranteDTO(
        @Schema(description = "Quantidade TOTAL de pedidos na história do restaurante", example = "1500")
        Long totalPedidosGeral,

        @Schema(description = "Lista de faturamento agrupada por dia")
        List<FaturamentoDiarioDTO> historicoFaturamento
) {

}
