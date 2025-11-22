package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.model.ItemPedido;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record ItemPedidoResponseDTO(

        Long idItemPedido,

        ProdutoResponseDTO produto,

        PedidoResponseDTO pedido,

        Integer qtQuantidade,

        BigDecimal vlPrecoUnitario,

        BigDecimal vlSubtotal,

        String dsObservacao,

        List<AdicionalResponseDTO> adicionaisIds
) {

    public  ItemPedidoResponseDTO(ItemPedido ip){
        this(
                ip.getIdItemPedido(),
                new ProdutoResponseDTO(ip.getProduto()),
                new PedidoResponseDTO(ip.getPedido()),
                ip.getQtQuantidade(),
                ip.getVlPrecoUnitario(),
                ip.getVlSubtotal(),
                ip.getDsObservacao(),
                ip.getAdicionais().stream().map(AdicionalResponseDTO::new).collect(Collectors.toList())
        );
    }
}
