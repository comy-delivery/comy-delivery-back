package com.comy_delivery_back.repository;

import com.comy_delivery_back.model.Entrega;
import com.comy_delivery_back.model.Entregador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntregadorRepository extends JpaRepository<Entregador, Long> {
    Optional<Entregador> findByCpfEntregador(String cpf);
    Optional<Entregador> findByEmailEntregador(String email);
    boolean existsByIsAtivoTrue();
    List<Entregador> findByIsDisponivelTrue();


    //revisar depois
    List<Entrega> findAllEntregasByEntregadorId(Long id);
}
