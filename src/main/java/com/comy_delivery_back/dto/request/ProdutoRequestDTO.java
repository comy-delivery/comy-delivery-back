package com.comy_delivery_back.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProdutoRequestDTO(
        @NotBlank(message = "Nome do produto é obrigatório")
        String nmProduto,

        @Size(max = 500, message = "A descrição do produto só pode ter 500 caracteres")
        String dsProduto,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser positivo")
        Double vlPreco,

        @NotNull(message = "ID do restaurante é obrigatório")
        Long restauranteId,

        @NotNull(message = "Categoria é obrigatória")
        @Size(max = 100, message = "A categoria só pode ter 100 caracteres")
        String categoriaProduto,

        Integer tempoPreparacao,

        @Positive(message = "Preço promocional deve ser positivo")
        Double vlPrecoPromocional


) {
}
