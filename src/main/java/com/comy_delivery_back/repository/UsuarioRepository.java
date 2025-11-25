package com.comy_delivery_back.repository;

import com.comy_delivery_back.enums.RoleUsuario;
import com.comy_delivery_back.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
    List<Usuario> findByIsAtivoTrue();
    List<Usuario> findByRoleUsuario(RoleUsuario roleUsuario);
}
