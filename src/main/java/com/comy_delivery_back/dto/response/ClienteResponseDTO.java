package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.model.Cliente;

import java.time.LocalDate;
import java.util.List;

public record ClienteResponseDTO(
        Long id,
        String username,
        String nmCliente,
        String emailCliente,
        String cpfCliente,
        String telefoneCliente,
        LocalDate dataCadastroCliente,
        List<EnderecoResponseDTO> enderecos,
        boolean isAtivo
) {}
