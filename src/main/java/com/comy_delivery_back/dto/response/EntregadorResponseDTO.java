package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.enums.VeiculoEntregador;
import com.comy_delivery_back.model.Entrega;
import com.comy_delivery_back.model.Entregador;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record EntregadorResponseDTO(
        Long id,
        String username,
        String nmEntregador,
        String emailEntregador,
        String cpfEntregador,
        String telefoneEntregador,
        VeiculoEntregador veiculo,
        String placa,
        LocalDate dataCadastroEntregador,
        //List<Long> idEntregas,
        boolean isAtivo
) {
    public EntregadorResponseDTO(Entregador e){
        this(
                e.getId(),
                e.getUsername(),
                e.getNmEntregador(),
                e.getEmailEntregador(),
                e.getCpfEntregador(),
                e.getTelefoneEntregador(),
                e.getVeiculo(),
                e.getPlaca(),
                e.getDataCadastroEntregador(),
                /*e.getEntregasEntregador() != null ?
                        e.getEntregasEntregador()
                        .stream()
                        .map(Entrega::getIdEntrega)
                        .collect(Collectors.toList()) : List.of(),*/
                e.isAtivo()
        );
    }
}
