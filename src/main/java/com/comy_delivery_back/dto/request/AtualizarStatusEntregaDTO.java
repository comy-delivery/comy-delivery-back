package com.comy_delivery_back.dto.request;

import com.comy_delivery_back.enums.StatusEntrega;

public record AtualizarStatusEntregaDTO(
        StatusEntrega statusEntrega,
        Long entregadorId
) {
}
