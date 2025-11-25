package com.comy_delivery_back.repository;

import com.comy_delivery_back.enums.StatusEntrega;
import com.comy_delivery_back.model.Entrega;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    Optional<Entrega> findByPedido_IdPedido(Long pedidoId);
    List<Entrega> findByStatusEntrega(StatusEntrega statusEntrega);
    List<Entrega> findByEntregadorId(Long entregadorId);
    List<Entrega> findByEntregadorIdAndStatusEntrega(Long entregadorId, StatusEntrega statusEntrega);

    @Query(value = "SELECT AVG(EXTRACT(EPOCH FROM (e.data_hora_conclusao - p.dt_criacao)) / 60) " +
            "FROM entrega e " +
            "JOIN pedido p ON e.pedido_id = p.id_pedido " +
            "WHERE p.restaurante_id = :restauranteId " +
            "AND e.status_entrega = 'CONCLUIDA' " +
            "AND e.data_hora_conclusao IS NOT NULL " +
            "AND p.dt_criacao IS NOT NULL", nativeQuery = true)
    Double calcularMediaTempoTotalPedido(@Param("restauranteId") Long restauranteId);
}
