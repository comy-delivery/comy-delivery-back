package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.enums.TipoCupom;
import com.comy_delivery_back.model.Cupom;
import io.swagger.v3.oas.annotations.media.Schema;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Resposta contendo dados de um cupom")
public record CupomResponseDTO(
        @Schema(description = "ID do cupom", example = "1")
        Long idCupom,

        @Schema(description = "Código do cupom", example = "BEMVINDO10")
        String codigoCupom,

        @Schema(description = "Descrição", example = "Desconto de boas-vindas")
        String dsCupom,

        @Schema(description = "Tipo do cupom", example = "VALOR_FIXO")
        TipoCupom tipoCupom,

        @Schema(description = "Valor do desconto fixo", example = "10.00")
        BigDecimal vlDesconto,

        @Schema(description = "Percentual de desconto", example = "15.00")
        BigDecimal percentualDesconto,

        @Schema(description = "Valor mínimo do pedido", example = "30.00")
        BigDecimal vlMinimoPedido,

        @Schema(description = "Data de validade", example = "2030-12-31T23:59:59")
        LocalDateTime dtValidade,

        @Schema(description = "Quantidade máxima de usos", example = "100")
        Integer qtdUsoMaximo,

        @Schema(description = "Quantidade já usada", example = "0")
        Integer qtdUsado,

        @Schema(description = "Status de ativo", example = "true")
        boolean isAtivo,

        @Schema(description = "ID do restaurante", example = "1")
        Long restauranteId,

        @Schema(description = "Data de criação", example = "2024-01-01T00:00:00")
        LocalDateTime dataCriacao
){
    public CupomResponseDTO(Cupom c){
        this(
                c.getIdCupom(),
                c.getCodigoCupom(),
                c.getDsCupom(),
                c.getTipoCupom(),
                c.getVlDesconto(),
                c.getPercentualDesconto(),
                c.getVlMinimoPedido(),
                c.getDtValidade(),
                c.getQtdUsoMaximo(),
                c.getQtdUsado(),
                c.isAtivo(),
                c.getRestaurante().getId(),
                c.getDataCriacao()
        );
    }
}
