package com.comy_delivery_back.repository;

import com.comy_delivery_back.model.Produto;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long> {

    List<Produto> findByRestaurante_Id(Long restauranteId);
    List<Produto> findByCategoriaProduto(String categoria);
    List<Produto> findByIsAtivoTrue();
    List<Produto> findByIsPromocaoTrue();
    List<Produto> findByRestaurante_IdAndIsAtivoTrue(Long restauranteId);

    @Query("SELECT AVG(p.vlPreco) FROM Produto p WHERE p.restaurante.id = :restauranteId AND p.isAtivo = true")
    Double calcularMediaPrecoPorRestaurante(@Param("restauranteId") Long restauranteId);

}
