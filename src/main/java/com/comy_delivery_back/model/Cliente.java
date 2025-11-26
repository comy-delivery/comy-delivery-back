package com.comy_delivery_back.model;

import com.comy_delivery_back.enums.RoleUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@AllArgsConstructor
@SuperBuilder
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Usuario{

    //seta automatico na criação do cliente
    public Cliente(){
        this.setRoleUsuario(RoleUsuario.CLIENTE);
    }

    @Column(nullable = false, length = 100)
    private String nmCliente;

    @Column(unique = true, nullable = false)
    private String emailCliente;

    @Column(unique = true, nullable = false)
    private String cpfCliente;

    @Column(length = 11, nullable = false)
    private String telefoneCliente;

    @OneToMany(
            mappedBy = "cliente"
    )
    @Builder.Default//correcao
    private List<Pedido> pedidos = new ArrayList<>();

    @OneToMany(
            mappedBy = "cliente",
            cascade = CascadeType.ALL
    )
    @Builder.Default//correcao
    private List<Endereco> enderecos = new ArrayList<>();

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataCadastroCliente = LocalDate.now();

}
