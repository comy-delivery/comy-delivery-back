package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.model.Produto;

import java.time.LocalDateTime;

public record ProdutoResponseDTO(
        Long idProduto,
 String nmProduto,
 String dsProduto,
 Double vlPreco,
 byte[] imagemProduto,
//     RestauranteResponseDTO restaurante;
 String categoriaProduto,
 Integer tempoPreparacao,
 Boolean isPromocao,
 Double vlPrecoPromocional,
 boolean isAtivo,
 LocalDateTime dataCadastro) {

    public ProdutoResponseDTO(Produto p){
        this(
                p.getIdProduto(),
                p.getNmProduto(),
                p.getDsProduto(),
                p.getVlPreco(),
                p.getImagemProduto(),
//                new RestauranteDTO(p.getIdRestaurante),
                p.getCategoriaProduto(),
                p.getTempoPreparacao(),
                p.getIsPromocao(),
                p.getVlPrecoPromocional(),
                p.isAtivo(),
                p.getDataCadastroProduto()
        );
    }


}
