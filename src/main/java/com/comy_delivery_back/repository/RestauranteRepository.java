package com.comy_delivery_back.repository;

import com.comy_delivery_back.enums.CategoriaRestaurante;
import com.comy_delivery_back.enums.DiasSemana;
import com.comy_delivery_back.model.Cliente;
import com.comy_delivery_back.model.Restaurante;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    Optional<Restaurante> findByCnpj(String cnpj);
    Optional<Restaurante> findByEmailRestaurante(String email);
    Optional<Restaurante> findByUsername(String username);
    List<Restaurante> findAllByIsAbertoTrue();
    List<Restaurante> findByIsDisponivelTrue();
    List<Restaurante> findByCategoria(CategoriaRestaurante categoriaRestaurante);
    Optional<Restaurante> findByTokenRecuperacaoSenha(String token);

    @Query("SELECT r FROM Restaurante r WHERE :dia MEMBER OF r.diasFuncionamento")
    List<Restaurante> findByDiaFuncionamento(@Param("dia") DiasSemana dia);
}
