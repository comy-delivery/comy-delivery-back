package com.comy_delivery_back.repository;

import com.comy_delivery_back.model.Adicional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdicionalRepository extends JpaRepository<Adicional, Long> {

    List<Adicional> findByProduto_IdProduto(Long produtoId);
    List<Adicional> findByIsDisponivelTrue();

}
