package com.comy_delivery_back.repository;

import com.comy_delivery_back.enums.CategoriaRestaurante;
import com.comy_delivery_back.enums.DiasSemana;
import com.comy_delivery_back.model.Restaurante;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    Optional<Restaurante> findByCpnj(String cnpj);
    List<Restaurante> findByIsAbertoTrue();
    List<Restaurante> findByIsDisponivelTrue();
    List<Restaurante> findByCategoria(CategoriaRestaurante categoriaRestaurante);

    @Query("SELECT r FROM Restaurante r WHERE :dia MEMBER OF r.diasFuncionamento")
    List<Restaurante> findByDiaFuncionamento(@Param("dia") DiasSemana dia);
}
