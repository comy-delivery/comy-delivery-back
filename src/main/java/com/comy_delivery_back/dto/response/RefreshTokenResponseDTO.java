package com.comy_delivery_back.dto.response;

public record RefreshTokenResponseDTO(
        String accessToken,
        String refreshToken
) {
}
