package com.comy_delivery_back.dto.request;

import jakarta.validation.constraints.NotNull;

public record EntregaRequestDTO(
        @NotNull
        Long pedidoId,
        Integer tempoEstimadoMinutos
) {
}
