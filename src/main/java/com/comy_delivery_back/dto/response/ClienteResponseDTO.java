package com.comy_delivery_back.dto.response;

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
        boolean isAtivoCliente
) {
    /*
    public ClienteResponseDTO(Cliente c){
        this(
                c.getId(),
                c.getUsername(),
                c.getNmCliente(),
                c.getEmailCliente(),
                c.getCpfCliente(),
                c.getTelefoneCliente(),
                c.getDataCadastroCliente(),
                //enderecos aqui
                c.getEnderecos(),
                c.isAtivoCliente()
        );
    }*/

}
