package com.comy_delivery_back.model;

import com.comy_delivery_back.enums.RoleUsuario;
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

    //seta automatico na criação do entregador
    public Entregador(){
        this.setRoleUsuario(RoleUsuario.ENTREGADOR);
    }

    @Column(nullable = false)
    private String nmEntregador;

    @Column(nullable = false, unique = true)
    private String emailEntregador;

    @Column(length = 11)
    private String telefoneEntregador;

    @Column(length = 11, nullable = false, unique = true)
    private String cpfEntregador;

    @Enumerated(EnumType.STRING)
    private VeiculoEntregador veiculo;

    @Column(nullable = false)
    private String placa;

    @Column(columnDefinition = "boolean default false")
    private boolean isDisponivel;

    private Double avaliacaoMediaEntregador;

    @OneToMany(
            mappedBy = "idUsuario",
            cascade = CascadeType.ALL
    )
    private List<Entrega> entregasEntregador;

    @Column(columnDefinition = "boolean default true")
    private boolean isAtivoEntregador;

}
