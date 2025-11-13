package com.comy_delivery_back.dto.response;

public record ViaCepDTO(
        String cep,
        String logradouro,

        String bairro,
        String localidade,
        String uf


) {
}
