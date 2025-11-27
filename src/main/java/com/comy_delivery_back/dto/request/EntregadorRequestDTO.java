package com.comy_delivery_back.dto.request;

import com.comy_delivery_back.enums.VeiculoEntregador;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

@Schema(description = "Dados para cadastro ou atualização de entregador")
public record EntregadorRequestDTO(

        @Schema(description = "Nome de usuário único para login",
                example = "entregador_jose",
                minLength = 4,
                maxLength = 50,
                required = true)
        @NotBlank(message = "O nome de usuario é obrigatório!")
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

        @Schema(description = "Nome completo do entregador",
                example = "José da Silva",
                maxLength = 100,
                required = true)
        @NotBlank
        @Size(max = 100, message = "O nome do entregador não pode exceder 100 caracteres.")
        String nmEntregador,

        @Schema(description = "Email do entregador",
                example = "jose.entregador@email.com",
                format = "email",
                required = true)
        @Email(message = "Formato de e-mail inválido")
        String emailEntregador,

        @Schema(description = "CPF do entregador (apenas números)",
                example = "12345678901",
                pattern = "\\d{11}",
                required = true)
        @CPF(message = "CPF inválido")
        String cpfEntregador,

        @Schema(description = "Telefone do entregador (10 ou 11 dígitos)",
                example = "11987654321",
                minLength = 10,
                maxLength = 11,
                required = true)
        @NotBlank
        @Size(min = 10, max = 11, message = "O telefone deve ter 10 ou 11 dígitos.")
        @Pattern(regexp = "^[0-9]{10,11}$", message = "O telefone deve conter apenas números.")
        String telefoneEntregador,

        @Schema(description = "Tipo de veículo utilizado para entregas",
                example = "MOTO",
                allowableValues = {"MOTO", "BICICLETA", "CARRO"},
                required = true)
        @NotNull(message = "O tipo do veículo é obrigatório!")
        VeiculoEntregador veiculo,

        @Schema(description = "Placa do veículo (7 caracteres, padrão brasileiro)",
                example = "ABC1D23",
                pattern = "[A-Z]{3}[0-9][A-Z0-9][0-9]{2}",
                minLength = 7,
                maxLength = 7)
        @Size(max = 7, min = 7, message = "A placa deve conter 7 caracteres")
        String placa
) {}
