package com.comy_delivery_back.model;

import com.comy_delivery_back.enums.RoleUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends Usuario{
    //seta automatico na criação do ADMIN
    public Admin(){
        this.setRoleUsuario(RoleUsuario.ADMIN);
    }

    @Column(nullable = false)
    private String nomeAdmin;

    @Column(unique = true, nullable = false)
    private String cpfAdmin;

    @Column(unique = true, nullable = false)
    private String emailAdmin;

}
