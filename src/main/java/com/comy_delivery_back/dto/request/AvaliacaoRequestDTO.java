package com.comy_delivery_back.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para criação de uma avaliação")
public record AvaliacaoRequestDTO(
        @Schema(description = "ID do restaurante avaliado", example = "1", required = true)
        @NotNull(message = "ID do restaurante é obrigatório")
        Long restauranteId,

        @Schema(description = "ID do cliente que está avaliando", example = "1", required = true)
        @NotNull(message = "ID do cliente é obrigatório")
        Long clienteId,

        @Schema(description = "ID do pedido avaliado", example = "1", required = true)
        @NotNull(message = "ID do pedido é obrigatório")
        Long pedidoId,

        @Schema(description = "ID do entregador avaliado", example = "1", required = true)
        @NotNull(message = "ID do entregador é obrigatório")
        Long entregadorId,

        @Schema(description = "Nota do restaurante (1-5)", example = "5", required = true, minimum = "1", maximum = "5")
        @NotNull(message = "Nota da comida é obrigatória")
        @Min(value = 1, message = "Nota mínima é 1")
        @Max(value = 5, message = "Nota máxima é 5")
        Integer nuNota,

        @Schema(description = "Comentário da avaliação", example = "Pizza excelente!", maxLength = 500)
        @Size(max = 500, message = "O comentário não pode ter mais de 500 caracteres")
        String dsComentario,

        @Schema(description = "Nota da entrega (1-5)", example = "5", minimum = "1", maximum = "5")
        @Min(value = 1, message = "Avaliação de entrega mínima é 1")
        @Max(value = 5, message = "Avaliação de entrega máxima é 5")
        Integer avaliacaoEntrega
) {}
