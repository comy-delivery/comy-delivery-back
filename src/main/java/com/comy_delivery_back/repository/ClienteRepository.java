package com.comy_delivery_back.repository;

import com.comy_delivery_back.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCpfCliente (String cpf);
    Optional<Cliente> findByEmailCliente(String email);
    boolean existsByIsAtivoTrue();
    List<Cliente> findAllByIsAtivoTrue();
    Optional<Cliente> findByTokenRecuperacaoSenha(String token);
}
