package com.comy_delivery_back.dto.response;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CupomResponseDTO (
        Long idCupom,
        String codigoCupom,
        String dsCupom,
        Double vlDesconto,
        Double percentualDesconto,
        Double vlMinimoPedido,
        LocalDateTime dtValidade,
        Integer qtdUsoMaximo,
        Integer qtdUsado,
        boolean isAtivo,
        Long restauranteId,
        LocalDateTime dataCriacao
){

}
