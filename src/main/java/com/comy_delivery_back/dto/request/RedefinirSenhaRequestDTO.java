package com.comy_delivery_back.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Dados para redefinição de senha via token de recuperação")
public record RedefinirSenhaRequestDTO(
        @Schema(description = "Token de recuperação de senha", example = "abc123-def456-ghi789", required = true)
        @NotBlank(message = "O token de recuperação é obrigatório.")
        String token,

        @Schema(description = "Nova senha (mínimo 8 caracteres, letras maiúsculas, minúsculas e números)",
                example = "NovaSenha123", required = true,
                pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")
        @NotBlank(message = "A nova senha é obrigatória.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, incluindo letras maiúsculas, minúsculas e números.")
        String novaSenha
) {}
