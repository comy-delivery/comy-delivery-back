package com.comy_delivery_back.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Classe para criar um novo produto")
public record ProdutoRequestDTO(
        @Schema(description = "Nome do produto", example = "Pizza Calabresa")
        @NotBlank(message = "Nome do produto é obrigatório")
        String nmProduto,

        @Schema(description = "Descrição do produto", example = "Pizza de calabresa com cebola")
        @Size(max = 500, message = "A descrição do produto só pode ter 500 caracteres")
        String dsProduto,

        @Schema(description = "Preço do produto", example = "35,00")
        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser positivo")
        BigDecimal vlPreco,

        @Schema(description = "Código de Id do Restaurante", example = "1")
        @NotNull(message = "ID do restaurante é obrigatório")
        Long restauranteId,

        @Schema(description = "Categoria do produto", example = "Pizzas")
        @NotNull(message = "Categoria é obrigatória")
        @Size(max = 100, message = "A categoria só pode ter 100 caracteres")
        String categoriaProduto,

        @Schema(description = "Tempo de Preparação", example = "15")
        Integer tempoPreparacao,

        @Schema(description = "Valor do preço promocional", example = "25,00")
        @Positive(message = "Preço promocional deve ser positivo")
        BigDecimal vlPrecoPromocional


) {
}
