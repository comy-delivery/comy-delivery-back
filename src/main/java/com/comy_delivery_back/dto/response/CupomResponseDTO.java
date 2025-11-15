package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.enums.TipoCupom;
import com.comy_delivery_back.model.Cupom;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CupomResponseDTO (
        Long idCupom,
        String codigoCupom,
        String dsCupom,
        TipoCupom tipoCupom,
        Double vlDesconto,
        Double percentualDesconto,
        Double vlMinimoPedido,
        LocalDateTime dtValidade,
        Integer qtdUsoMaximo,
        Integer qtdUsado,
        boolean isAtivo,
        Long restauranteId,
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
                c.getRestaurante().getIdUsuario(),
                c.getDataCriacao()
        );
    }

}
