package com.comy_delivery_back.dto.request;

import com.comy_delivery_back.enums.TipoEndereco;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AtualizarEnderecoRequestDTO (

        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,

        @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
        @NotNull
        String cep,

        String estado,
        TipoEndereco tipoEndereco,
        String pontoDeReferencia,
        Double latitude,
        Double longitude
) {
}
