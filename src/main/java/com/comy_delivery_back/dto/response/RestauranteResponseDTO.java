package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.enums.CategoriaRestaurante;
import com.comy_delivery_back.enums.DiasSemana;
import com.comy_delivery_back.model.Produto;
import com.comy_delivery_back.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64; //Base64 para codificação
import java.util.List;

public record RestauranteResponseDTO(
        Long id,
        String username,
        String nmRestaurante,
        String emailRestaurante,
        String cnpj,
        String telefoneRestaurante,
        String imagemLogo,
        String descricaoRestaurante,
        List<EnderecoResponseDTO> enderecos,
        CategoriaRestaurante categoria,
        @JsonFormat(pattern = "HH:mm")
        LocalTime horarioAbertura,
        @JsonFormat(pattern = "HH:mm")
        LocalTime horarioFechamento,
        List<DiasSemana> diasFuncionamento,
        List<Long> produtosId,
        Integer tempoMediaEntrega,
        Double avaliacaoMediaRestaurante,
        boolean isAberto,
        boolean isDisponivel,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate dataCadastro,
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
                restaurante.getImagemLogo() != null
                        ? Base64.getEncoder().encodeToString(restaurante.getImagemLogo()) : null,
                restaurante.getDescricaoRestaurante(),
                restaurante.getEnderecos().stream().map(EnderecoResponseDTO::new).toList(),
                restaurante.getCategoria(),
                restaurante.getHorarioAbertura(),
                restaurante.getHorarioFechamento(),
                restaurante.getDiasFuncionamento(),
                restaurante.getProdutos() != null
                        ? restaurante.getProdutos().stream().map(Produto::getIdProduto).toList() : List.of(),
                restaurante.getTempoMediaEntrega(),
                restaurante.getAvaliacaoMediaRestaurante(),
                restaurante.isAberto(),
                restaurante.isDisponivel(),
                restaurante.getDataCadastro(),
                restaurante.isAtivo()

        );
    }
}
