package com.comy_delivery_back.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Schema(description = "Dados para cadastro de novo cliente")
public record ClienteRequestDTO(

        @Schema(description = "Nome de usuário único para login",
                example = "cliente_maria",
                minLength = 4,
                maxLength = 50,
                required = true)
        @NotBlank(message = "O nome de usuário é obrigatório!")
        @Size(min = 4, max = 50, message = "O nome de usuário deve ter entre 4 e 50 caracteres.")
        String username,

        @Schema(description = "Senha forte (mínimo 8 caracteres, com letras maiúsculas, minúsculas e números)",
                example = "SenhaForte123",
                pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
                required = true)
        @NotBlank
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, incluindo letras maiúsculas, minúsculas e números.")
        String password,

        @Schema(description = "Nome completo do cliente",
                example = "Maria da Silva",
                maxLength = 100,
                required = true)
        @NotBlank
        @Size(max = 100, message = "O nome do cliente não pode exceder 100 caracteres.")
        String nmCliente,

        @Schema(description = "Email do cliente",
                example = "maria@email.com",
                format = "email",
                required = true)
        @Email(message = "Formato de e-mail inválido")
        String emailCliente,

        @Schema(description = "CPF do cliente (apenas números)",
                example = "12345678901",
                pattern = "\\d{11}",
                required = true)
        @CPF(message = "CPF inválido")
        String cpfCliente,

        @Schema(description = "Telefone do cliente (10 ou 11 dígitos)",
                example = "11987654321",
                minLength = 10,
                maxLength = 11,
                required = true)
        @NotBlank
        @Size(min = 10, max = 11, message = "O telefone deve ter 10 ou 11 dígitos.")
        @Pattern(regexp = "^[0-9]{10,11}$", message = "O telefone deve conter apenas números.")
        String telefoneCliente,

        @Schema(description = "Lista de endereços do cliente (pelo menos 1 é obrigatório)",
                required = true)
        @NotNull(message = "É obrigatório a inserção de pelo menos um endereço")
        List<EnderecoRequestDTO> enderecos

) {}
