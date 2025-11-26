package com.comy_delivery_back.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "Dados de um item a ser adicionado ao pedido")
public record ItemPedidoRequestDTO(

        @Schema(description = "ID do produto", example = "1", required = true)
        @NotNull(message = "ID do produto é obrigatório")
        Long produtoId,

        @Schema(description = "ID do pedido", example = "1", required = true)
        @NotNull(message = "ID do pedido é obrigatório")
        Long pedidoId,

        @Schema(description = "Quantidade do produto", example = "2", required = true, minimum = "1")
        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser positiva")
        Integer qtQuantidade,

        @Schema(description = "Observações do item", example = "Sem cebola", maxLength = 300)
        @Size(max = 300, message = "A observação do item só pode ter 300 caracteres")
        String dsObservacao,

        @Schema(description = "IDs dos adicionais selecionados", example = "[1, 2]")
        List<Long> adicionaisIds
) {}
