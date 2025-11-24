package com.comy_delivery_back.dto.request;

import com.comy_delivery_back.enums.FormaPagamento;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PedidoRequestDTO(

        @NotNull(message = "ID do cliente é obrigatório")
        Long cliente,

        @NotNull(message = "ID do restaurante é obrigatório")
        Long restaurante,

        @NotNull(message = "ID do endereço de entrega é obrigatório")
        Long enderecoEntregaId,

        @NotNull(message = "ID do endereço de origem (restaurante) é obrigatório")
        Long enderecoOrigemId,

        Long cupomId,

        @NotEmpty(message = "Pedido deve conter pelo menos um item")
        List<ItemPedidoRequestDTO> itensPedido,

        @NotNull(message = "Forma de pagamento é obrigatória")
        FormaPagamento formaPagamento,

        @Size(max = 500, message = "A observação não deve ter mais de 500 caracteres")
        String dsObservacoes

) {
}
