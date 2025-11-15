package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.enums.TipoEndereco;
import com.comy_delivery_back.model.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoResponseDTO(

        Long idEndereco,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String cep,
        String estado,
        TipoEndereco tipoEndereco,
        String pontoDeReferecia,
        boolean isPadrao,
        Double latitude,
        Double longitude
//        CLienteDTO cliente,
//        RestauranDTO restaurante
) {

    public EnderecoResponseDTO (Endereco e){
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
                e.getPontoDeReferecia(),
                e.isPadrao(),
                e.getLatitude(),
                e.getLongitude()
//                new Cliente  ResponseDTO(p.getIdCliente),
//                new RestauranteResponseDTO(p.getIdRestaurante),
        );
    }
}
