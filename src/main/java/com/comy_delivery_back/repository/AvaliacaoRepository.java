package com.comy_delivery_back.repository;

import com.comy_delivery_back.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao,Long> {

    List<Avaliacao> findByRestaurante_Id(Long restauranteId);
    List<Avaliacao> findByCliente_Id(Long clienteId);
    List<Avaliacao> findByEntregador_Id(Long entregadorId);

    @Query("SELECT AVG(a.nuNota) FROM Avaliacao a WHERE a.restaurante.id = :restauranteId")
    Double calcularMediaAvaliacaoRestaurante(Long restauranteId);

    @Query("SELECT AVG(a.avaliacaoEntrega) FROM Avaliacao a WHERE a.entregador.id = :entregadorId")
    Double calcularMediaAvaliacaoEntregador(Long entregadorId);

}
