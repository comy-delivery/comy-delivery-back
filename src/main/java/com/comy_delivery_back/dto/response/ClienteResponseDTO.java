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
        List<PedidoResponseDTO> pedidos,
        boolean isAtivo
) {
    public ClienteResponseDTO(Cliente cliente){
        this(
                cliente.getId(),
                cliente.getUsername(),
                cliente.getNmCliente(),
                cliente.getEmailCliente(),
                cliente.getCpfCliente(),
                cliente.getTelefoneCliente(),
                cliente.getDataCadastroCliente(),
                cliente.getEnderecos().stream().map(EnderecoResponseDTO::new).toList(),
                cliente.getPedidos().stream().map(PedidoResponseDTO::new).toList(),
                cliente.isAtivo()
        );

    }
}
