package com.comy_delivery_back.repository;

import com.comy_delivery_back.enums.StatusPedido;
import com.comy_delivery_back.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {

    List<Pedido> findByCliente_IdCliente(Long clienteId);
    List<Pedido> findByRestaurante_IdRestaurante(Long restauranteId);
    List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findByRestaurante_IdRestauranteAndStatus(Long restauranteId, StatusPedido status);

}
