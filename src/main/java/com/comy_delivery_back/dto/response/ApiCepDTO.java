package com.comy_delivery_back.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta da API de CEP com informações do endereço")
public record ApiCepDTO(
        @Schema(description = "CEP", example = "01001000")
        String cep,

        @Schema(description = "Logradouro", example = "Praça da Sé")
        String address,

        @Schema(description = "Bairro", example = "Sé")
        String district,

        @Schema(description = "Cidade", example = "São Paulo")
        String city,

        @Schema(description = "Estado", example = "SP")
        String state,

        @Schema(description = "Latitude", example = "-23.550520")
        String lat,

        @Schema(description = "Longitude", example = "-46.633308")
        String lng
) {

}
