package com.comy_delivery_back.repository;

import com.comy_delivery_back.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao,Long> {

    List<Avaliacao> findByRestaurante_IdRestaurante(Long restauranteId);
    List<Avaliacao> findByUsuario_IdUsuario(Long usuarioId);
    List<Avaliacao> findByEntregador_IdEntregador(Long entregadorId);

    @Query("SELECT AVG(a.nuNota) FROM Avaliacao a WHERE a.restaurante.idRestaurante = :restauranteId")
    Double calcularMediaAvaliacaoRestaurante(Long restauranteId);

    @Query("SELECT AVG(a.avaliacaoEntrega) FROM Avaliacao a WHERE a.entregador.idEntregador = :entregadorId")
    Double calcularMediaAvaliacaoEntregador(Long entregadorId);

}
