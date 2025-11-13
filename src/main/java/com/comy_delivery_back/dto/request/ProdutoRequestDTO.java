package com.comy_delivery_back.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProdutoRequestDTO(
        @NotBlank(message = "Nome do produto é obrigatório")
        String nmProduto,

        String dsProduto,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser positivo")
        Double vlPreco,

        byte[] imagemProduto,

//@NotNull(message = "ID do restaurante é obrigatório")
//Long restauranteId,

        @NotNull(message = "Categoria é obrigatória")
        String categoriaProduto,

        Integer tempoPreparacao,

        Boolean isPromocao,

        @Positive(message = "Preço promocional deve ser positivo")
        Double vlPrecoPromocional


) {
}
