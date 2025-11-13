package com.comy_delivery_back.repository;

import com.comy_delivery_back.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Long> {
//    List<Endereco> findByCliente_IdUsuario(Long clienteId);
//    List<Endereco> findByRestaurante_IdRestaurante(Long restauranteId);


}
