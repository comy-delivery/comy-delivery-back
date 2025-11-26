package com.comy_delivery_back.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Dados para criação ou atualização de um adicional")
public record AdicionalRequestDTO(

        @Schema(description = "Nome do adicional", example = "Borda Recheada", required = true)
        @NotBlank(message = "Nome do adicional é obrigatório")
        String nmAdicional,

        @Schema(description = "Descrição do adicional", example = "Borda de Catupiry Original", maxLength = 100)
        @Size(max = 100, message = "A descrição do adicional não pode ter mais que 100 caracters")
        String dsAdicional,

        @Schema(description = "Preço do adicional", example = "5.00", required = true, minimum = "0.01")
        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser positivo")
        BigDecimal vlPrecoAdicional,

        @Schema(description = "ID do produto ao qual o adicional pertence", example = "1", required = true)
        @NotNull(message = "O id do produto é obrigatório")
        Long produtoId
) {}
