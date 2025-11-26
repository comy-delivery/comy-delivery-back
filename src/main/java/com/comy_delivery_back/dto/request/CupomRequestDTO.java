package com.comy_delivery_back.dto.request;

import com.comy_delivery_back.enums.TipoCupom;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Dados para criação ou atualização de cupom de desconto")
public record CupomRequestDTO(

        @Schema(description = "Código único do cupom (usado pelo cliente)",
                example = "PRIMEIRACOMPRA10",
                minLength = 10,
                maxLength = 50,
                required = true)
        @NotBlank(message = "O código do cupom deve conter entre 10 e 50 caracteres")
        @Size(min = 10, max = 50)
        String codigoCupom,

        @Schema(description = "Descrição explicativa do cupom",
                example = "10% de desconto na primeira compra",
                maxLength = 100)
        @Size(max = 100, message = "A descrição do cupom não pode ter mais que 100 caracteres")
        String dsCupom,

        @Schema(description = "Tipo de desconto aplicado",
                example = "PERCENTUAL",
                allowableValues = {"VALOR_FIXO", "PERCENTUAL"},
                required = true)
        @NotNull(message = "O tipo do cupom precisa ser preenchido")
        TipoCupom tipoCupom,

        @Schema(description = "Valor fixo de desconto em reais (usar quando tipoCupom = VALOR_FIXO)",
                example = "15.00",
                minimum = "0.01")
        BigDecimal vlDesconto,

        @Schema(description = "Percentual de desconto (usar quando tipoCupom = PERCENTUAL)",
                example = "10.00",
                minimum = "0.01",
                maximum = "100")
        BigDecimal percentualDesconto,

        @Schema(description = "Valor mínimo do pedido para usar o cupom",
                example = "50.00",
                minimum = "0")
        @Positive(message = "O pedido precisa ter um mínimo positivo")
        BigDecimal vlMinimoPedido,

        @Schema(description = "Data de validade do cupom (formato dd-MM-yyyy)",
                example = "31-12-2025",
                type = "string",
                pattern = "dd-MM-yyyy",
                required = true)
        @NotNull(message = "Data de validade é obrigatória")
        @FutureOrPresent(message = "Data de validade deve ser presente ou futura")
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate dtValidade,

        @Schema(description = "Quantidade máxima de vezes que o cupom pode ser usado",
                example = "100",
                minimum = "1",
                required = true)
        @NotNull(message = "Precisa definir um limite")
        @Min(value = 1, message = "Limite mínimo é 1")
        Integer qtdUsoMaximo,

        @Schema(description = "ID do restaurante que criou o cupom",
                example = "1",
                required = true)
        @NotNull(message = "ID do restaurante é obrigatório")
        Long restauranteId

) {}

