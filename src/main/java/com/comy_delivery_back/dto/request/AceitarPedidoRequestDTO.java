package com.comy_delivery_back.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para aceitar ou recusar um pedido")
public record AceitarPedidoRequestDTO(
        @Schema(description = "Indica se o pedido foi aceito", example = "true", required = true)
        boolean aceitar,

        @Schema(description = "Motivo da recusa (obrigatório se aceitar = false)", example = "Ingredientes em falta")
        String motivoRecusa
) {}
