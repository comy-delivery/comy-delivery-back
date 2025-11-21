package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.enums.StatusEntrega;

import java.time.LocalDateTime;

public record EntregaResponseDTO (
        Long id,
        Long pedidoId,
        Long entregadorId,
        StatusEntrega statusEntrega,
        Long enderecoOrigemId,
        Long enderecoDestinoId,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraConclusao,
        Integer tempoEstimadoMinutos,
        Double valorEntrega,
        Double avaliacaoCliente
){
}
