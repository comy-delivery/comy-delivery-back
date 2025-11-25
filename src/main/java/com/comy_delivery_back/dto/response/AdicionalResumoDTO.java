package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.model.Adicional;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Resumo do adicional")
public record AdicionalResumoDTO(
        @Schema(description = "Nome do adicional", example = "Borda de Catupiry")
        String nome,

        @Schema(description = "Preço do adicional", example = "5.00")
        BigDecimal preco
) {
    public AdicionalResumoDTO(Adicional adicional) {
        this(adicional.getNmAdicional(), adicional.getVlPrecoAdicional());
    }
}