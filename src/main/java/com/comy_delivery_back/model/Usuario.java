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

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private boolean recuperar;

    @Column(nullable = false)
    private RoleUsuario roleUsuario;
}
