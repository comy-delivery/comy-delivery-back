package com.comy_delivery_back.dto.request;

import com.comy_delivery_back.enums.CategoriaRestaurante;
import com.comy_delivery_back.enums.DiasSemana;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalTime;
import java.util.List;

public record RestauranteRequestDTO(
        @NotBlank(message = "O nome de usuário é obrigatório.")
        @Size(min = 4, max = 50, message = "O nome de usuário deve ter entre 4 e 50 caracteres.")
        String username,

        @NotBlank(message = "A senha é obrigatória.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
                message = "A senha deve ter no mínimo 8 caracteres, incluindo letras maiúsculas, minúsculas e números.")
        String password,

        @NotBlank(message = "O nome do restaurante é obrigatório.")
        @Size(max = 100, message = "O nome não pode exceder 100 caracteres.")
        String nmRestaurante,

        @Email(message = "Formato de e-mail inválido.")
        @NotBlank(message = "O e-mail é obrigatório.")
        String emailRestaurante,

        @CNPJ(message = "CNPJ inválido.")
        @NotBlank(message = "O CNPJ é obrigatório.")
        String cnpj,

        @Size(min = 10, max = 11, message = "O telefone deve ter 10 ou 11 dígitos.")
        @Pattern(regexp = "^[0-9]{10,11}$", message = "O telefone deve conter apenas números.")
        String telefoneRestaurante,

        //se for Base64:
        @Size(max = 3000000, message = "O tamanho da imagem não deve exceder 3MB (após codificação base64).")
        String imagemLogo,

        @Size(max = 500, message = "A descrição não pode exceder 500 caracteres.")
        String descricaoRestaurante,

        @NotEmpty(message = "Deve ser fornecido pelo menos um endereço inicial.")
        List<EnderecoRequestDTO> enderecos,

        @NotNull(message = "A categoria é obrigatória.")
        CategoriaRestaurante categoria,

        @NotNull(message = "O horário de abertura é obrigatório.")
        LocalTime horarioAbertura,

        @NotNull(message = "O horário de fechamento é obrigatório.")
        LocalTime horarioFechamento,

        @NotEmpty(message = "Os dias de funcionamento são obrigatórios.")
        List<DiasSemana> diasFuncionamento,

        List<ProdutoRequestDTO> produtos,

        //@NotNull(message = "O tempo médio de entrega é obrigatório.")
        @Min(value = 5, message = "O tempo mínimo de entrega é de 5 minutos.")
        Integer tempoMediaEntrega
) {
}
