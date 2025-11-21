package com.comy_delivery_back.dto.request;

import com.comy_delivery_back.enums.TipoEndereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoRequestDTO(

        @NotBlank(message = "O nome da rua é necessário")
        String logradouro,

        @NotBlank(message = "O numero do endereço é necessário")
        String numero,

        String complemento,

        String bairro,

        String cidade,

        @NotBlank(message = "O cep é necessário")
        @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
        String cep,

        String estado,

        @NotBlank(message = "O tipo do endereço é necessário")
        TipoEndereco tipoEndereco,

        String pontoDeReferencia,

        Double latitude,

        Double longitude
) {
}
