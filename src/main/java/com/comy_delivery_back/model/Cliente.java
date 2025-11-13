package com.comy_delivery_back.model;

import com.comy_delivery_back.enums.RoleUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
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

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime dataCadastroCliente = LocalDateTime.now();

    @OneToMany(
            mappedBy = "idUsuario"
    )
    private List<Endereco> enderecos;

    @Column(columnDefinition = "boolean default true")
    private boolean isAtivoCliente;
}
