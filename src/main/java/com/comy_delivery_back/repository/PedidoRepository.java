package com.comy_delivery_back.repository;

import com.comy_delivery_back.enums.StatusPedido;
import com.comy_delivery_back.model.Pedido;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {

    List<Pedido> findByCliente_Id(Long clienteId);

    List<Pedido> findByRestaurante_Id(Long restauranteId);

    List<Pedido> findByStatus(StatusPedido status);

    List<Pedido> findByRestaurante_IdAndStatus(Long restauranteId, StatusPedido status);

    @Query("SELECT p FROM Pedido p WHERE p.restaurante.id = :restauranteId AND p.isAceito = true")
    List<Pedido> findPedidosAceitosByRestaurante(@Param("restauranteId") Long restauranteId);

    // Buscar pedidos recusados por restaurante
    @Query("SELECT p FROM Pedido p WHERE p.restaurante.id = :restauranteId AND p.isAceito = false AND p.motivoRecusa IS NOT NULL")
    List<Pedido> findPedidosRecusadosByRestaurante(@Param("restauranteId") Long restauranteId);

    // Buscar pedidos pendentes de aceitação
    @Query("SELECT p FROM Pedido p WHERE p.restaurante.id = :restauranteId AND p.status = 'PENDENTE' AND p.isAceito = false")
    List<Pedido> findPedidosPendentesDeAceitacao(@Param("restauranteId") Long restauranteId);

    // Buscar pedidos por endereço de entrega
    List<Pedido> findByEnderecoEntrega_IdEndereco(Long enderecoId);

    // Buscar pedidos por endereço de origem
    List<Pedido> findByEnderecoOrigem_IdEndereco(Long enderecoId);

    // Contar pedidos aceitos por restaurante
    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.restaurante.id = :restauranteId AND p.isAceito = true")
    Long countPedidosAceitosByRestaurante(@Param("restauranteId") Long restauranteId);

    // Contar pedidos recusados por restaurante
    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.restaurante.id = :restauranteId AND p.isAceito = false AND p.motivoRecusa IS NOT NULL")
    Long countPedidosRecusadosByRestaurante(@Param("restauranteId") Long restauranteId);
}
