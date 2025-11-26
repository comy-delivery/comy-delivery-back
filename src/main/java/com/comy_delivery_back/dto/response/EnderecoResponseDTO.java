package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.enums.TipoEndereco;
import com.comy_delivery_back.model.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Resposta contendo dados de um endereço")
public record EnderecoResponseDTO(

        @Schema(description = "ID do endereço", example = "1")
        Long idEndereco,

        @Schema(description = "Logradouro", example = "Av. Paulista")
        String logradouro,

        @Schema(description = "Número", example = "1000")
        String numero,

        @Schema(description = "Complemento", example = "Apto 101")
        String complemento,

        @Schema(description = "Bairro", example = "Bela Vista")
        String bairro,

        @Schema(description = "Cidade", example = "São Paulo")
        String cidade,

        @Schema(description = "CEP", example = "01310100")
        String cep,

        @Schema(description = "Estado", example = "SP")
        String estado,

        @Schema(description = "Tipo do endereço", example = "CASA")
        TipoEndereco tipoEndereco,

        @Schema(description = "Ponto de referência", example = "Próximo ao metrô")
        String pontoDeReferencia,

        @Schema(description = "É endereço padrão?", example = "true")
        boolean isPadrao,

        @Schema(description = "Latitude", example = "-23.561684")
        Double latitude,

        @Schema(description = "Longitude", example = "-46.655981")
        Double longitude,

        @Schema(description = "ID do cliente (se aplicável)", example = "1")
        Long clienteId,

        @Schema(description = "ID do restaurante (se aplicável)", example = "1")
        Long restauranteId
) {
    public EnderecoResponseDTO(Endereco e){
        this(
                e.getIdEndereco(),
                e.getLogradouro(),
                e.getNumero(),
                e.getComplemento(),
                e.getBairro(),
                e.getCidade(),
                e.getCep(),
                e.getEstado(),
                e.getTipoEndereco(),
                e.getPontoDeReferencia(),
                e.isPadrao(),
                e.getLatitude(),
                e.getLongitude(),
                e.getCliente() != null ? e.getCliente().getId() : null,
                e.getRestaurante() != null ? e.getRestaurante().getId() : null
        );
    }
}
