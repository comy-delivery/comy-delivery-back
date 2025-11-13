package com.comy_delivery_back.model;

import com.comy_delivery_back.enums.RoleUsuario;
import jakarta.persistence.*;
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
public class Cliente extends Usuario{

    //seta automatico na criação do cliente
    public Cliente(){
        this.setRoleUsuario(RoleUsuario.CLIENTE);
    }

    @Column(nullable = false)
    private String nmCliente;

    @Column(unique = true, nullable = false)
    private String emailCliente;

    @Column(unique = true, nullable = false)
    private String cpfCliente;

    @Column(length = 11, nullable = false)
    private String telefoneCliente;

    @OneToMany(
            mappedBy = "idUsuario"
    )
    private List<Endereco> enderecos;

    private LocalDateTime dataCadastroCliente;

    @Column(columnDefinition = "boolean default true")
    private boolean isAtivoCliente;
}
