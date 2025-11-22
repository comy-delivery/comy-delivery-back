package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.model.Produto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Classe para armazenar as informações de um produto")
public record ProdutoResponseDTO(
        @Schema(description = "Número de identificação do cadastro no banco", example = "1")
        Long idProduto,

        @Schema(description = "Nome do produto cadastrado", example = "Pizza calabresa")
        String nmProduto,

        @Schema(description = "Descrição do produto", example = "Pizza de calabresa com cebola")
        String dsProduto,

        @Schema(description = "Valor do preço do produto", example = "35,00")
        BigDecimal vlPreco,

        @Schema(description = "Imagem de apresentação do produto", format = "byte")
        byte[] imagemProduto,

        @Schema(description = "Restaurante que cadastrou o produto", example = "id:1, nmRestaurante: Palácio da pizza...")
        RestauranteResponseDTO restaurante,

        @Schema(description = "Categoria de produtos", example = "Pizzas")
        String categoriaProduto,

        @Schema(description = "Tempo de preparação desse produto", example = "20")
        Integer tempoPreparacao,

        @Schema(description = "Flag de marcação para Promoção", example = "true", format = "boolean")
        Boolean isPromocao,

        @Schema(description = "Valor do preço do  quando promocional", example = "35,00")
        BigDecimal vlPrecoPromocional,

        @Schema(description = "Flag de marcação para Promoção", example = "true", format = "boolean")
        boolean isAtivo,

        @Schema(description = "Data de cadastro do produto", format = "22-12-2025")
        LocalDateTime dataCadastro) {

    public ProdutoResponseDTO(Produto p){
        this(
                p.getIdProduto(),
                p.getNmProduto(),
                p.getDsProduto(),
                p.getVlPreco(),
                p.getImagemProduto(),
                new RestauranteResponseDTO(p.getRestaurante()),
                p.getCategoriaProduto(),
                p.getTempoPreparacao(),
                p.getIsPromocao(),
                p.getVlPrecoPromocional(),
                p.isAtivo(),
                p.getDataCadastroProduto()
        );
    }


}
