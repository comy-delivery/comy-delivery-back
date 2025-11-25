package com.comy_delivery_back.dto.response;

import java.math.BigDecimal;

public record RestauranteDistanciaDTO(RestauranteResponseDTO restaurante,
                                      Double distanciaKm,
                                      BigDecimal valorFreteEstimado,
                                      Integer tempoEstimadoEntrega,
                                      BigDecimal mediaPrecoProdutos) {
}
