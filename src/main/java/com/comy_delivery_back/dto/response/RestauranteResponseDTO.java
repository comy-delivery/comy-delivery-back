package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.enums.CategoriaRestaurante;
import com.comy_delivery_back.enums.DiasSemana;
import com.comy_delivery_back.model.Produto;
import com.comy_delivery_back.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64; //Base64 para codificação
import java.util.List;

@Schema(description = "Resposta contendo dados completos de um restaurante")
public record RestauranteResponseDTO(

        @Schema(description = "ID único do restaurante", example = "1")
        Long id,

        @Schema(description = "Nome de usuário para login", example = "pizzaria_top")
        String username,

        @Schema(description = "Nome do restaurante", example = "Pizzaria Top")
        String nmRestaurante,

        @Schema(description = "Email de contato", example = "contato@pizzariatop.com")
        String emailRestaurante,

        @Schema(description = "CNPJ do restaurante", example = "12345678000199")
        String cnpj,

        @Schema(description = "Telefone de contato", example = "11999990000")
        String telefoneRestaurante,

        @Schema(description = "Descrição do restaurante",
                example = "A melhor pizza artesanal da região",
                maxLength = 500)
        String descricaoRestaurante,

        @Schema(description = "Lista de endereços do restaurante")
        List<EnderecoResponseDTO> enderecos,

        @Schema(description = "Categoria principal do restaurante",
                example = "PIZZA",
                allowableValues = {"LANCHE", "PIZZA", "DOCE", "ASIATICA", "BRASILEIRA", "SAUDAVEL"})
        CategoriaRestaurante categoria,

        @Schema(description = "Horário de abertura (formato HH:mm)",
                example = "18:00",
                type = "string",
                pattern = "HH:mm")
        @JsonFormat(pattern = "HH:mm")
        LocalTime horarioAbertura,

        @Schema(description = "Horário de fechamento (formato HH:mm)",
                example = "23:59",
                type = "string",
                pattern = "HH:mm")
        @JsonFormat(pattern = "HH:mm")
        LocalTime horarioFechamento,

        @Schema(description = "Dias da semana em que funciona",
                example = "[\"SEXTA\", \"SABADO\", \"DOMINGO\"]")
        List<DiasSemana> diasFuncionamento,

        @Schema(description = "IDs dos produtos cadastrados",
                example = "[1, 2, 3]")
        List<Long> produtosId,

        @Schema(description = "Tempo médio de entrega em minutos (calculado automaticamente)",
                example = "45")
        Integer tempoMediaEntrega,

        @Schema(description = "Avaliação média do restaurante (0-5)",
                example = "4.8",
                minimum = "0",
                maximum = "5")
        Double avaliacaoMediaRestaurante,

        @Schema(description = "Indica se o restaurante está aberto no momento",
                example = "true")
        boolean isAberto,

        @Schema(description = "Indica se o restaurante está disponível para pedidos",
                example = "true")
        boolean isDisponivel,

        @Schema(description = "Data de cadastro no sistema (formato dd-MM-yyyy)",
                example = "01-01-2024",
                type = "string",
                pattern = "dd-MM-yyyy")
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate dataCadastro,

        @Schema(description = "Indica se o restaurante está ativo (não deletado)",
                example = "true")
        boolean isAtivo
) {
    public RestauranteResponseDTO(Restaurante restaurante){
        this(
                restaurante.getId(),
                restaurante.getUsername(),
                restaurante.getNmRestaurante(),
                restaurante.getEmailRestaurante(),
                restaurante.getCnpj(),
                restaurante.getTelefoneRestaurante(),
                restaurante.getDescricaoRestaurante(),
                restaurante.getEnderecos().stream().map(EnderecoResponseDTO::new).toList(),
                restaurante.getCategoria(),
                restaurante.getHorarioAbertura(),
                restaurante.getHorarioFechamento(),
                restaurante.getDiasFuncionamento(),
                restaurante.getProdutos() != null
                        ? restaurante.getProdutos().stream().map(Produto::getIdProduto).toList()
                        : List.of(),
                restaurante.getTempoMediaEntrega(),
                restaurante.getAvaliacaoMediaRestaurante(),
                restaurante.isAberto(),
                restaurante.isDisponivel(),
                restaurante.getDataCadastro(),
                restaurante.isAtivo()
        );
    }
}
