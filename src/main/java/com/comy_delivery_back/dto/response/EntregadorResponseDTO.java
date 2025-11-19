package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.enums.VeiculoEntregador;
import com.comy_delivery_back.model.Entrega;
import com.comy_delivery_back.model.Entregador;

import java.time.LocalDate;
import java.util.List;

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
        List<Long> entregarId,
        boolean isAtivo
) {
    public EntregadorResponseDTO(Entregador entregador){
        this(
                entregador.getId(),
                entregador.getUsername(),
                entregador.getNmEntregador(),
                entregador.getEmailEntregador(),
                entregador.getCpfEntregador(),
                entregador.getTelefoneEntregador(),
                entregador.getVeiculo(),
                entregador.getPlaca(),
                entregador.getDataCadastroEntregador(),
                entregador.getEntregasEntregador().stream().map(Entrega::getIdEntrega).toList(),
                entregador.isAtivo()
        );
    }
}
