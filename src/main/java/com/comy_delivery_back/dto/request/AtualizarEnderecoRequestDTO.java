package com.comy_delivery_back.dto.request;

import com.comy_delivery_back.enums.TipoEndereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Dados para atualização de um endereço")
public record AtualizarEnderecoRequestDTO(

        @Schema(description = "Nome da rua/avenida", example = "Av. Paulista")
        String logradouro,

        @Schema(description = "Número do imóvel", example = "1000")
        String numero,

        @Schema(description = "Complemento", example = "Apto 101")
        String complemento,

        @Schema(description = "Bairro", example = "Bela Vista")
        String bairro,

        @Schema(description = "Cidade", example = "São Paulo")
        String cidade,

        @Schema(description = "CEP sem formatação (8 dígitos)", example = "01310100", pattern = "\\d{8}", required = true)
        @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
        @NotNull
        String cep,

        @Schema(description = "Estado (UF)", example = "SP")
        String estado,

        @Schema(description = "Tipo do endereço", example = "CASA")
        TipoEndereco tipoEndereco,

        @Schema(description = "Ponto de referência", example = "Próximo ao metrô")
        String pontoDeReferencia,

        @Schema(description = "Latitude", example = "-23.561684")
        Double latitude,

        @Schema(description = "Longitude", example = "-46.655981")
        Double longitude
) {}
