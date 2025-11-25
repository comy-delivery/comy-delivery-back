package com.comy_delivery_back.model;

import com.comy_delivery_back.enums.RoleUsuario;
import com.comy_delivery_back.enums.VeiculoEntregador;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@PrimaryKeyJoinColumn(name = "id")
public class Entregador extends Usuario{

    //seta automatico na criação do Entregador
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

    @Column(length = 7)
    private String placa;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataCadastroEntregador = LocalDate.now();

    @Column(columnDefinition = "boolean default false")
    private boolean isDisponivel;

    private Double avaliacaoMediaEntregador;

    @OneToMany(
            mappedBy = "entregador",
            cascade = CascadeType.ALL
    )
    private List<Entrega> entregasEntregador;


}
