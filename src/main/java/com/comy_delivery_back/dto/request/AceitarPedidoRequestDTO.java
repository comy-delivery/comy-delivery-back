package com.comy_delivery_back.dto.request;

public record AceitarPedidoRequestDTO(
        boolean aceitar,
        String motivoRecusa
) {
}
