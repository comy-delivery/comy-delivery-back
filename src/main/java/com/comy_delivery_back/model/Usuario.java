package com.comy_delivery_back.model;

import com.comy_delivery_back.enums.RoleUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleUsuario roleUsuario;

    @Column(columnDefinition = "boolean default false")
    private boolean recuperar;

    @Column(columnDefinition = "boolean default true")
    private boolean isAtivo;

    private String tokenRecuperacaoSenha;
    private LocalDateTime expiracaoToken;

    @PrePersist
    public void prePersist(){
         isAtivo = true;
    }

}
