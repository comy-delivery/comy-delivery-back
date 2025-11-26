package com.comy_delivery_back.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de autenticação bem-sucedida")
public record LoginResponseDTO(
        @Schema(description = "Token JWT para autenticação",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String jwt,

        @Schema(description = "ID do usuário autenticado", example = "1")
        Long userId
) {}
