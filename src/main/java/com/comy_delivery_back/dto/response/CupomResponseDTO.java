package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.enums.TipoCupom;
import com.comy_delivery_back.model.Cupom;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CupomResponseDTO (
        Long idCupom,
        String codigoCupom,
        String dsCupom,
        TipoCupom tipoCupom,
        BigDecimal vlDesconto,
        BigDecimal percentualDesconto,
        BigDecimal vlMinimoPedido,
        LocalDateTime dtValidade,
        Integer qtdUsoMaximo,
        Integer qtdUsado,
        boolean isAtivo,
        RestauranteResponseDTO restauranteId,
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
                new RestauranteResponseDTO(c.getRestaurante()),
                c.getDataCriacao()
        );
    }

}
