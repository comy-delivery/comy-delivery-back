package com.comy_delivery_back.repository;

import com.comy_delivery_back.model.Adicional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdicionalRepository extends JpaRepository<Adicional, Long> {
}
