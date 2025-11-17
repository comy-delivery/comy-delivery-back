package com.comy_delivery_back.dto.request;

import com.comy_delivery_back.enums.TipoCupom;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CupomRequestDTO(

        @NotBlank(message = "O codigo do cupom de conter entre 10 e 50 caracteres")
        @Size(min = 10, max = 50)
        String codigoCupom,

        @Size(max=100, message = "A descrição do cupom não pode ter mais que 100 caracteres")
        String dsCupom,

        @NotBlank(message = "O tipo do cupom precisa ser preenchido")
        TipoCupom tipoCupom,

        Double vlDesconto,

        Double percentualDesconto,

        @Positive(message = "O pedido precisa ter um minimo positivo")
        Double vlMinimoPedido,

        @NotNull(message = "Data de validade é obrigatória")
        @FutureOrPresent
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDateTime dtValidade,

        Integer qtdUsoMaximo,

        @NotNull(message = "ID do restaurante é obrigatório")
        Long restauranteId

) {
}
