package com.comy_delivery_back.model;

import com.comy_delivery_back.enums.CategoriaRestaurante;
import com.comy_delivery_back.enums.DiasSemana;
import com.comy_delivery_back.enums.RoleUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@PrimaryKeyJoinColumn(name = "id")
public class Restaurante extends Usuario{

    //seta automatico na criação do Restaurante
    public Restaurante(){
        this.setRoleUsuario(RoleUsuario.RESTAURANTE);
    }

    @Column(nullable = false)
    private String nmRestaurante;
    @Column(unique = true, nullable = false)
    private String emailRestaurante;

    @Column(length = 14, unique = true, nullable = false)
    private String cnpj;

    @Column(length = 11)
    private String telefoneRestaurante;

    @Lob
    private byte[] imagemLogo;

    @Column(length = 500)
    private String descricaoRestaurante;

    @OneToMany(
            mappedBy = "restaurante",
            cascade = CascadeType.ALL
    )
    private List<Endereco> enderecos;

    @Column
    private CategoriaRestaurante categoria;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horarioAbertura;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horarioFechamento;

    @ElementCollection //para lista de enums
    @Enumerated(EnumType.STRING)
    private List<DiasSemana> diasFuncionamento;

    private Integer tempoMediaEntrega;

    private Double avaliacaoMediaRestaurante;

    private boolean isAberto;

    private boolean isDisponivel;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataCadastro = LocalDate.now();

    @Column(columnDefinition = "boolean default true")
    private boolean isAtivoRestaurante;
}
