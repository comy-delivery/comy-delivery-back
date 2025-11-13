package com.comy_delivery_back.model;

import com.comy_delivery_back.enums.RoleUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleUsuario roleUsuario;

    @Column(columnDefinition = "boolean default false")
    private boolean recuperar;

    //possibilita settar nas classes filhas
    public RoleUsuario getRoleUsuario(){
        return roleUsuario;
    }

    public void setRoleUsuario(RoleUsuario roleUsuario){
        this.roleUsuario = roleUsuario;
    }
}
