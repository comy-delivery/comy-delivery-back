package com.comy_delivery_back.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de cadastro bem-sucedido")
public record SignupResponseDTO(
        @Schema(description = "ID do usuário criado", example = "1")
        Long id,

        @Schema(description = "Nome de usuário cadastrado", example = "novo_usuario")
        String username
) {}
