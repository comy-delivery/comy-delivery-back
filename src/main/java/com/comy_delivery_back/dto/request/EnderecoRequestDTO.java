package com.comy_delivery_back.dto.request;

import com.comy_delivery_back.enums.TipoEndereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Dados para cadastro de um novo endereço")
public record EnderecoRequestDTO(

        @Schema(description = "Nome da rua/avenida", example = "Av. Paulista", required = true)
        @NotBlank(message = "O nome da rua é necessário")
        String logradouro,

        @Schema(description = "Número do imóvel", example = "1000", required = true)
        @NotBlank(message = "O numero do endereço é necessário")
        String numero,

        @Schema(description = "Complemento", example = "Apto 101")
        String complemento,

        @Schema(description = "Bairro", example = "Bela Vista")
        String bairro,

        @Schema(description = "Cidade", example = "São Paulo")
        String cidade,

        @Schema(description = "CEP sem formatação (8 dígitos)", example = "01310100", pattern = "\\d{8}", required = true)
        @NotBlank(message = "O cep é necessário")
        @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
        String cep,

        @Schema(description = "Estado (UF)", example = "SP")
        String estado,

        @Schema(description = "Tipo do endereço", example = "CASA", required = true)
        @NotNull(message = "O tipo do endereço é necessário")
        TipoEndereco tipoEndereco,

        @Schema(description = "Ponto de referência", example = "Próximo ao metrô")
        String pontoDeReferencia,

        @Schema(description = "Latitude", example = "-23.561684")
        Double latitude,

        @Schema(description = "Longitude", example = "-46.655981")
        Double longitude
) {}
