package com.comy_delivery_back.dto.request;

import com.comy_delivery_back.enums.FormaPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "Dados para criação de um novo pedido")
public record PedidoRequestDTO(

        @Schema(description = "ID do cliente que está fazendo o pedido",
                example = "1",
                required = true)
        @NotNull(message = "ID do cliente é obrigatório")
        Long cliente,

        @Schema(description = "ID do restaurante",
                example = "1",
                required = true)
        @NotNull(message = "ID do restaurante é obrigatório")
        Long restaurante,

        @Schema(description = "ID do endereço de entrega (deve pertencer ao cliente)",
                example = "2",
                required = true)
        @NotNull(message = "ID do endereço de entrega é obrigatório")
        Long enderecoEntregaId,

        @Schema(description = "ID do endereço de origem/restaurante",
                example = "1",
                required = true)
        @NotNull(message = "ID do endereço de origem (restaurante) é obrigatório")
        Long enderecoOrigemId,

        @Schema(description = "ID do cupom de desconto (opcional)",
                example = "1")
        Long cupomId,

        @Schema(description = "Lista de itens do pedido com produtos, quantidades e adicionais",
                required = true)
        @NotEmpty(message = "Pedido deve conter pelo menos um item")
        List<ItemPedidoRequestDTO> itensPedido,

        @Schema(description = "Forma de pagamento escolhida",
                example = "CREDITO",
                required = true,
                allowableValues = {"CREDITO", "DEBITO", "PIX", "DINHEIRO"})
        @NotNull(message = "Forma de pagamento é obrigatória")
        FormaPagamento formaPagamento,

        @Schema(description = "Observações adicionais do cliente",
                example = "Entregar na portaria",
                maxLength = 500)
        @Size(max = 500, message = "A observação não deve ter mais de 500 caracteres")
        String dsObservacoes

) {}

