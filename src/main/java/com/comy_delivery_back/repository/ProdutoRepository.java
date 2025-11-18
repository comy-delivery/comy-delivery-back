package com.comy_delivery_back.repository;

import com.comy_delivery_back.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long> {

    List<Produto> findByRestaurante_IdRestaurante(Long restauranteId);
    List<Produto> findByCategoria(String categoria);
    List<Produto> findByIsAtivoTrue();
    List<Produto> findByIsPromocaoTrue();
    List<Produto> findByRestaurante_IdRestauranteAndIsAtivoTrue(Long restauranteId);

}
