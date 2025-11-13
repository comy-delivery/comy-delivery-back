package com.comy_delivery_back.model;

import com.comy_delivery_back.enums.CategoriaRestaurante;
import com.comy_delivery_back.enums.DiasSemana;
import com.comy_delivery_back.enums.RoleUsuario;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@PrimaryKeyJoinColumn(name = "idUsuario")
public class Restaurante extends Usuario{
    public Restaurante(){
        this.setRoleUsuario(RoleUsuario.RESTAURANTE);
    }

    private String nmRestaurante;
    private String emailRestaurante;
    private String cnpj;
    private String telefoneRestaurante;

    //mudar imagemLogo;

    private String descricaoRestaurante;
    private List<Endereco> enderecos;
    private CategoriaRestaurante categoria;
    private LocalDateTime horarioAbertura;
    private LocalDateTime horarioFechamento;
    private List<DiasSemana> diasFuncionamento;
    private Integer tempoMediaEntrega;
    private Double taxaEntrega;
    private Double avaliacaoMediaRestaurante;
    private boolean isAberto;
    private boolean isDisponivel;
    private boolean isAtivoRestaurante;
    private LocalDateTime dataCadastro;
}
