package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.enums.StatusEntrega;
import com.comy_delivery_back.model.Entrega;

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
    public EntregaResponseDTO(Entrega entrega){
        this(
                entrega.getIdEntrega(),
                entrega.getPedido().getIdPedido(),
                entrega.getEntregador() != null ? entrega.getEntregador().getId() : null,
                entrega.getStatusEntrega(),
                entrega.getEnderecoOrigem() != null ? entrega.getEnderecoOrigem().getIdEndereco() : null,
                entrega.getEnderecoDestino() != null ? entrega.getEnderecoDestino().getIdEndereco() : null,
                entrega.getDataHoraInicio(),
                entrega.getDataHoraConclusao(),
                entrega.getTempoEstimadoMinutos(),
                entrega.getVlEntrega(),
                entrega.getAvaliacaoCliente()
        );
    }
}
