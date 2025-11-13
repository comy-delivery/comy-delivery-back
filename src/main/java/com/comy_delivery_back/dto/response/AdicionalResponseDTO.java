package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.model.Adicional;

public record AdicionalResponseDTO(

        Long idAdicional,
        String nmAdicional,
        String dsAdicional,
        Double vlPrecoAdicional,
        ProdutoResponseDTO produto,
        boolean isDisponivel
) {

    public AdicionalResponseDTO(Adicional a){
        this(
                a.getIdAdicional(),
                a.getNmAdicional(),
                a.getDsAdicional(),
                a.getVlPrecoAdicional(),
                new ProdutoResponseDTO(a.getProduto()),
                a.isDisponivel()
        );
    }
}
