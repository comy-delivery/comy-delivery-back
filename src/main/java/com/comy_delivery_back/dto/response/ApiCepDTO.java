package com.comy_delivery_back.dto.response;

public record ApiCepDTO(
        String cep,
        String address,
        String district,
        String city,
        String state,
        String lat,
        String lng
) {
}
