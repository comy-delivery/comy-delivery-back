package com.comy_delivery_back.dto.request;

import com.comy_delivery_back.enums.RoleUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para criação de nova conta")
public record SignupRequestDTO(
        @Schema(description = "Nome de usuário único", example = "novo_usuario", required = true)
        @NotBlank(message = "Username é obrigatório")
        String username,

        @Schema(description = "Senha forte", example = "SenhaForte123", required = true)
        @NotBlank(message = "Password é obrigatório")
        String password,

        @Schema(description = "Tipo de usuário", example = "CLIENTE", required = true,
                allowableValues = {"CLIENTE", "RESTAURANTE", "ENTREGADOR", "ADMIN"})
        @NotNull(message = "Role é obrigatória")
        RoleUsuario role
) {}
