package com.comy_delivery_back.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ItemPedidoRequestDTO(

        @NotNull(message = "ID do produto é obrigatório")
        Long produtoId,

        @NotNull(message = "ID do pedido é obrigatório")
        Long pedidoId,

        @NotNull(message = "Quantidade é obrigatória")
        @Positive (message = "Quantidade deve ser positiva")
        Integer qtQuantidade,

        @Size(max = 300, message = "A obsevação do item só pode ter 300 caracteres")
        String dsObservacao,

        List<Long>adicionaisIds

) {
}
