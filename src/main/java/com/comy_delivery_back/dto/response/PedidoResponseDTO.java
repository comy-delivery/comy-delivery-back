package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.enums.FormaPagamento;
import com.comy_delivery_back.enums.StatusPedido;
import com.comy_delivery_back.model.Pedido;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "Resposta contendo dados completos de um pedido")
public record PedidoResponseDTO(

        @Schema(description = "ID único do pedido", example = "1")
        Long idPedido,

        @Schema(description = "ID do cliente que fez o pedido", example = "3")
        Long clienteId,

        @Schema(description = "ID do restaurante", example = "2")
        Long restauranteId,

        @Schema(description = "Endereço completo de entrega")
        EnderecoResponseDTO enderecoEntrega,

        @Schema(description = "Endereço de origem (restaurante)")
        EnderecoResponseDTO enderecoOrigem,

        @Schema(description = "Cupom aplicado ao pedido (se houver)")
        CupomResponseDTO cupom,

        @Schema(description = "Lista de itens do pedido com produtos e adicionais")
        List<ItemPedidoResponseDTO> itensPedido,

        @Schema(description = "Data e hora de criação do pedido",
                example = "2024-11-26T19:00:00")
        LocalDateTime dtCriacao,

        @Schema(description = "Data e hora da última atualização",
                example = "2024-11-26T19:05:00")
        LocalDateTime dtAtualizacao,

        @Schema(description = "Subtotal (soma dos itens, sem frete e desconto)",
                example = "45.00")
        BigDecimal vlSubtotal,

        @Schema(description = "Valor da entrega calculado pela distância",
                example = "5.00")
        BigDecimal vlEntrega,

        @Schema(description = "Valor do desconto aplicado (cupom)",
                example = "10.00")
        BigDecimal vlDesconto,

        @Schema(description = "Valor total do pedido (subtotal + frete - desconto)",
                example = "40.00")
        BigDecimal vlTotal,

        @Schema(description = "Status atual do pedido",
                example = "CONFIRMADO",
                allowableValues = {"PENDENTE", "CONFIRMADO", "EM_PREPARO", "PRONTO",
                        "SAIU_PARA_ENTREGA", "ENTREGUE", "CANCELADO"})
        StatusPedido status,

        @Schema(description = "Forma de pagamento escolhida",
                example = "CREDITO",
                allowableValues = {"CREDITO", "DEBITO", "PIX", "DINHEIRO"})
        FormaPagamento formaPagamento,

        @Schema(description = "Tempo estimado de entrega em minutos",
                example = "45")
        Integer tempoEstimadoEntrega,

        @Schema(description = "Observações do cliente",
                example = "Entregar na portaria")
        String dsObservacoes,

        @Schema(description = "Indica se o pedido foi aceito pelo restaurante",
                example = "true")
        boolean isAceito,

        @Schema(description = "Data e hora que o restaurante aceitou o pedido",
                example = "2024-11-26T19:01:00")
        LocalDateTime dtAceitacao,

        @Schema(description = "Motivo da recusa (se o pedido foi recusado)",
                example = "Ingredientes em falta")
        String motivoRecusa

) {

    public PedidoResponseDTO(Pedido p){
        this(
                p.getIdPedido(),
                p.getCliente().getId(),
                p.getRestaurante().getId(),
                new EnderecoResponseDTO(p.getEnderecoEntrega()),
                new EnderecoResponseDTO(p.getEnderecoOrigem()),
                p.getCupom() != null ? new CupomResponseDTO(p.getCupom()) : null,
                p.getItensPedido().stream().map(ItemPedidoResponseDTO::new).collect(Collectors.toList()),
                p.getDtCriacao(),
                p.getDtAtualizacao(),
                p.getVlSubtotal(),
                p.getVlEntrega(),
                p.getVlDesconto(),
                p.getVlTotal(),
                p.getStatus(),
                p.getFormaPagamento(),
                p.getTempoEstimadoEntrega(),
                p.getDsObservacoes(),
                p.isAceito(),
                p.getDtAceitacao(),
                p.getMotivoRecusa()
        );
    }
}
