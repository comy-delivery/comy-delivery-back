package com.comy_delivery_back.model;

import com.comy_delivery_back.enums.VeiculoEntregador;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@PrimaryKeyJoinColumn(name = "idUsuario")
public class Entregador extends Usuario{

    @Column(nullable = false)
    private String nmEntregador;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 11)
    private String telefone;

    @Column(length = 11, nullable = false, unique = true)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private VeiculoEntregador veiculo;

    @Column(nullable = false)
    private String placa;

    @Column(columnDefinition = "boolean default false")
    private boolean isDisponivel;

    private Double avaliacaoMedia;

    @OneToMany(
            mappedBy = "idUsuario",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Entrega> entregas;

    @Column(columnDefinition = "boolean default true")
    private boolean isAtivo;

}
