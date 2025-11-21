package com.comy_delivery_back.repository;

import com.comy_delivery_back.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Long> {

    Optional<Endereco> findByIdEndereco(Long idEndereco);

    List<Endereco> findByCliente_Id(Long clienteId);
    List<Endereco> findByRestaurante_Id(Long restauranteId);


}
