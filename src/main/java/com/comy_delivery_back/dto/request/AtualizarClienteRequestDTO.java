package com.comy_delivery_back.dto.request;

import com.comy_delivery_back.model.Endereco;

public record AtualizarClienteRequestDTO(
        String nmCliente,
        String emailCliente,
        String telefoneCliente
        //Endereco enderecoPrincipal criar atualizarEndereco
) {
}
