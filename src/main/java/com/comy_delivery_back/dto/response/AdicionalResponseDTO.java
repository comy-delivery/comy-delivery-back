package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.model.Adicional;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Resposta contendo dados de um adicional")
public record AdicionalResponseDTO(

        @Schema(description = "ID do adicional", example = "1")
        Long idAdicional,

        @Schema(description = "Nome do adicional", example = "Borda Recheada")
        String nmAdicional,

        @Schema(description = "Descrição do adicional", example = "Borda de Catupiry Original")
        String dsAdicional,

        @Schema(description = "Preço do adicional", example = "5.00")
        BigDecimal vlPrecoAdicional,

        @Schema(description = "ID do produto", example = "1")
        Long produtoId,

        @Schema(description = "Indica se o adicional está disponível", example = "true")
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
