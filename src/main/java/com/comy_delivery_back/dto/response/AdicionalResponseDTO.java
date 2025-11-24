package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.model.Adicional;

import java.math.BigDecimal;

public record AdicionalResponseDTO(

        Long idAdicional,
        String nmAdicional,
        String dsAdicional,
        BigDecimal vlPrecoAdicional,
        Long produtoId,
        boolean isDisponivel
) {

    public AdicionalResponseDTO(Adicional a){
        this(
                a.getIdAdicional(),
                a.getNmAdicional(),
                a.getDsAdicional(),
                a.getVlPrecoAdicional(),
                a.getProduto().getIdProduto(),
                a.isDisponivel()
        );
    }
}
