package com.comy_delivery_back.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AdicionalRequestDTO(

        @NotBlank(message = "Nome do adicional é obrigatório")
        String nmAdicional,

        String dsAdicional,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser positivo")
        Double vlPrecoAdicional,

        @NotNull(message = "O id do produto é obrigatório")
        Long produtoId

) {
}
