package com.comy_delivery_back.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteRequestDTO(
        @NotBlank(message = "O nome de usuario é obrigatório!")
        @Size(min = 4, max = 50, message = "O nome de usuário deve ter entre 4 e 50 caracteres.")
        String username,

        @NotBlank
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, incluindo letras maiúsculas, minúsculas e números.")
        String password,

        @NotBlank
        @Size(max = 100, message = "O nome do cliente não pode exceder 100 caracteres.")
        String nmCliente,

        @Email(message = "Formato de e-mail inválido")
        String emailCliente,

        @CPF(message = "CPF inválido")
        String cpfCliente,

        @NotBlank
        @Size(min = 10, max = 11, message = "O telefone deve ter 10 ou 11 dígitos.")
        @Pattern(regexp = "^[0-9]{10,11}$", message = "O telefone deve conter apenas números.")
        String telefoneCliente
) {
}
