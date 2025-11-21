package com.comy_delivery_back.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AvaliacaoRequestDTO(
        @NotNull(message = "ID do restaurante é obrigatório")
        Long restauranteId,

        @NotNull(message = "ID do cliente é obrigatório")
        Long clienteId,

        @NotNull(message = "ID do pedido é obrigatório")
        Long pedidoId,

        @NotNull(message = "ID do entregador é obrigatório")
        Long entregadorId,

        @NotNull(message = "Nota da comida é obrigatória")
        @Min(value = 1, message = "Nota mínima é 1")
        @Max(value = 5, message = "Nota máxima é 5")
        Integer nuNota,

        @Size(max = 500,message = "O comentário não pode ter mais de 500 caracteres" )
        String dsComentario,

        @Min(value = 1, message = "Avaliação de entrega mínima é 1")
        @Max(value = 5, message = "Avaliação de entrega máxima é 5")
        Integer avaliacaoEntrega

) {
}
