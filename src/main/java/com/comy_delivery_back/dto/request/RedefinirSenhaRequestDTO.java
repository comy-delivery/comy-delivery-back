package com.comy_delivery_back.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RedefinirSenhaRequestDTO(
        @NotBlank(message = "O token de recuperação é obrigatório.")
        String token,

        @NotBlank(message = "A nova senha é obrigatória.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, incluindo letras maiúsculas, minúsculas e números.")
        String novaSenha
) {
}
