package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.model.ItemPedido;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "Resposta contendo dados completos de um item do pedido")
public record ItemPedidoResponseDTO(

        @Schema(description = "ID do item do pedido", example = "1")
        Long idItemPedido,

        @Schema(description = "Dados completos do produto")
        ProdutoResponseDTO produto,

        @Schema(description = "ID do pedido ao qual este item pertence", example = "1")
        Long pedido,

        @Schema(description = "Quantidade do produto", example = "2", minimum = "1")
        Integer qtQuantidade,

        @Schema(description = "Preço unitário no momento do pedido (pode diferir do preço atual)",
                example = "40.00")
        BigDecimal vlPrecoUnitario,

        @Schema(description = "Subtotal do item (quantidade × preço unitário + adicionais)",
                example = "85.00")
        BigDecimal vlSubtotal,

        @Schema(description = "Observações do cliente sobre este item",
                example = "Bem passado, sem cebola",
                maxLength = 300)
        String dsObservacao,

        @Schema(description = "Lista de adicionais selecionados para este item")
        List<AdicionalResponseDTO> adicionaisIds
) {

    public ItemPedidoResponseDTO(ItemPedido ip){
        this(
                ip.getIdItemPedido(),
                new ProdutoResponseDTO(ip.getProduto()),
                ip.getPedido().getIdPedido(),
                ip.getQtQuantidade(),
                ip.getVlPrecoUnitario(),
                ip.getVlSubtotal(),
                ip.getDsObservacao(),
                ip.getAdicionais().stream().map(AdicionalResponseDTO::new).collect(Collectors.toList())
        );
    }
}
