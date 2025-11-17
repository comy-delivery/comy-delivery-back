package com.comy_delivery_back.repository;

import com.comy_delivery_back.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmailAdmin(String email);
    Optional<Admin> findByCpfAdmin(String cpf);
    boolean existsByIsAtivoTrue();
}
