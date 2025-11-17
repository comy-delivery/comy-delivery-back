package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.enums.CategoriaRestaurante;
import com.comy_delivery_back.enums.DiasSemana;
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
        //List<EnderecoResponseDTO> enderecos,
        CategoriaRestaurante categoria,
        @JsonFormat(pattern = "HH:mm")
        LocalTime horarioAbertura,
        @JsonFormat(pattern = "HH:mm")
        LocalTime horarioFechamento,
        List<DiasSemana> diasFuncionamento,
        Integer tempoMediaEntrega,
        Double avaliacaoMediaRestaurante,
        boolean isAberto,
        boolean isDisponivel,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate dataCadastro,
        boolean isAtivo
) {
    public RestauranteResponseDTO(Restaurante r){
        this(
                r.getId(),
                r.getUsername(),
                r.getNmRestaurante(),
                r.getEmailRestaurante(),
                r.getCnpj(),
                r.getTelefoneRestaurante(),
                //convertendo para byte p/ string
                r.getImagemLogo() != null ?
                        Base64.getEncoder().encodeToString(r.getImagemLogo()) :
                        null,
                r.getDescricaoRestaurante(),
                //r.getEnderecos(),
                r.getCategoria(),
                r.getHorarioAbertura(),
                r.getHorarioFechamento(),
                r.getDiasFuncionamento(),
                r.getTempoMediaEntrega(),
                r.getAvaliacaoMediaRestaurante(),
                r.isAberto(),
                r.isDisponivel(),
                r.getDataCadastro(),
                r.isAtivo()
        );
    }
}
