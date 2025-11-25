package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.model.ItemPedido;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Resumo do item do pedido")
public record ItemPedidoResumoDTO(
        @Schema(description = "Nome do produto", example = "Pizza Calabresa")
        String nomeProduto,

        @Schema(description = "Quantidade", example = "1")
        Integer quantidade,

        @Schema(description = "Observação do item", example = "Sem cebola")
        String observacao,

        @Schema(description = "Lista de adicionais")
        List<AdicionalResumoDTO> adicionais
) {
    public ItemPedidoResumoDTO(ItemPedido item) {
        this(
                item.getProduto().getNmProduto(),
                item.getQtQuantidade(),
                item.getDsObservacao(),
                item.getAdicionais().stream()
                        .map(AdicionalResumoDTO::new)
                        .toList()
        );
    }
}
