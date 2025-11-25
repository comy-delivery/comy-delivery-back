package com.comy_delivery_back.repository;

import com.comy_delivery_back.model.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, Long> {

    Optional<Cupom> findByCodigoCupom(String codigoCupom);
    List<Cupom> findByIsAtivoTrue();
    List<Cupom> findByRestaurante_Id(Long restauranteId);

}
