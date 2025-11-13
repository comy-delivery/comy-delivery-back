package com.comy_delivery_back.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CupomRequestDTO(

        @NotBlank(message = "O codigo do cupom de conter entre 10 e 50 caracteres")
        @Size(min = 10, max = 50)
        String codigoCupom,

        String dsCupom,

        Double vlDesconto,

        Double percentualDesconto,

        @Positive(message = "O pedido precisa ter um minimo positivo")
        Double vlMinimoPedido,

        @NotNull(message = "Data de validade é obrigatória")
        @FutureOrPresent
        LocalDateTime dtValidade,

        Integer qtdUsoMaximo,

        @NotNull(message = "ID do restaurante é obrigatório")
        Long restauranteId

) {
}
