package com.comy_delivery_back.dto.request;

import com.comy_delivery_back.enums.StatusEntrega;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

@Schema(description = "Dados para atualização do status de uma entrega")
public record AtualizarStatusEntregaDTO(
        @Schema(description = "Novo status da entrega", example = "EM_ROTA", required = true,
                allowableValues = {"PENDENTE", "EM_ROTA", "CONCLUIDA", "CANCELADA"})
        StatusEntrega statusEntrega,

        @Schema(description = "ID do entregador (obrigatório ao iniciar entrega)", example = "1")
        Long entregadorId,

        @Schema(description = "Avaliação do cliente (1-5, obrigatório ao concluir)", example = "5.0", minimum = "1", maximum = "5")
        @DecimalMin(value = "1.0", message = "Avaliação mínima é 1")
        @DecimalMax(value = "5.0", message = "Avaliação máxima é 5")
        Double avaliacaoCliente
) {}