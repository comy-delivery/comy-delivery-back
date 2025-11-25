package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.enums.StatusPedido;
import com.comy_delivery_back.model.Pedido;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Resumo do pedido para listagens (com itens)")
public record PedidoResumoDTO(
        @Schema(description = "ID do pedido", example = "1")
        Long idPedido,

        @Schema(description = "Nome do restaurante", example = "Pizzaria Top")
        String nomeRestaurante,

        @Schema(description = "Data do pedido")
        LocalDateTime dataPedido,

        @Schema(description = "Valor total", example = "50.00")
        BigDecimal valorTotal,

        @Schema(description = "Status atual", example = "ENTREGUE")
        StatusPedido status,

        @Schema(description = "Itens do pedido")
        List<ItemPedidoResumoDTO> itens
) {

    public PedidoResumoDTO(Pedido pedido) {
        this(
                pedido.getIdPedido(),
                pedido.getRestaurante().getNmRestaurante(),
                pedido.getDtCriacao(),
                pedido.getVlTotal(),
                pedido.getStatus(),
                pedido.getItensPedido().stream()
                        .map(ItemPedidoResumoDTO::new)
                        .toList()
        );
    }
}
