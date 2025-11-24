package com.comy_delivery_back.dto.response;

public record LoginResponseDTO(
        String jwt,
        Long userId
) {
}
