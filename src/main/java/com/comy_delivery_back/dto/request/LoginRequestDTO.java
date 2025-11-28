package com.comy_delivery_back.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credenciais para autenticação")
public record LoginRequestDTO(
        @Schema(description = "Nome de usuário", example = "cliente_joao", required = true)
        @NotBlank(message = "Username é obrigatório")
        String username,

        @Schema(description = "Senha do usuário", example = "SenhaForte123", required = true)
        @NotBlank(message = "Password é obrigatório")
        String password
) {}
