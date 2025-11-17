package com.comy_delivery_back.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AdicionalRequestDTO(

        @NotBlank(message = "Nome do adicional é obrigatório")
        String nmAdicional,

        @Size(max = 100, message = "A descrição do adicional não pode ter mais que 100 caracters")
        String dsAdicional,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser positivo")
        Double vlPrecoAdicional,

        @NotNull(message = "O id do produto é obrigatório")
        Long produtoId

) {
}
