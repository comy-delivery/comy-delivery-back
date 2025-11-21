package com.comy_delivery_back.repository;

import com.comy_delivery_back.enums.StatusEntrega;
import com.comy_delivery_back.model.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    Optional<Entrega> findByPedido_IdPedido(Long pedidoId);
    List<Entrega> findByStatusEntrega(StatusEntrega statusEntrega);
    List<Entrega> findByEntregadorId(Long entregadorId);
    List<Entrega> findByEntregadorIdAndStatusEntrega(Long entregadorId, StatusEntrega statusEntrega);
}
