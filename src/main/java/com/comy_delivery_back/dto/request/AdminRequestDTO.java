package com.comy_delivery_back.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

@Schema(description = "Dados para cadastro de administrador do sistema")
public record AdminRequestDTO(

        @Schema(description = "Nome de usuário único para login",
                example = "admin_sistema",
                minLength = 4,
                maxLength = 50,
                required = true)
        @NotBlank(message = "O nome de usuário é obrigatório!")
        @Size(min = 4, max = 50, message = "O nome de usuário deve ter entre 4 e 50 caracteres.")
        String username,

        @Schema(description = "Senha forte (mínimo 8 caracteres, com letras maiúsculas, minúsculas e números)",
                example = "AdminForte123",
                pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
                required = true)
        @NotBlank
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, incluindo letras maiúsculas, minúsculas e números.")
        String password,

        @Schema(description = "Nome completo do administrador",
                example = "Administrador do Sistema",
                maxLength = 100,
                required = true)
        @NotBlank
        @Size(max = 100, message = "O nome do administrador não pode exceder 100 caracteres.")
        String nmAdmin,

        @Schema(description = "Email do administrador",
                example = "admin@comy.com",
                format = "email",
                required = true)
        @Email(message = "Formato de e-mail inválido")
        String emailAdmin,

        @Schema(description = "CPF do administrador (apenas números)",
                example = "12345678901",
                pattern = "\\d{11}",
                required = true)
        @CPF(message = "CPF inválido")
        String cpfAdmin
) {}
