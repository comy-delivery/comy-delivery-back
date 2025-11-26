package com.comy_delivery_back.dto.request;

import com.comy_delivery_back.model.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para atualização de informações do cliente")
public record AtualizarClienteRequestDTO(
        @Schema(description = "Nome completo do cliente", example = "João da Silva")
        String nmCliente,

        @Schema(description = "Email do cliente", example = "joao@email.com")
        String emailCliente,

        @Schema(description = "Telefone do cliente", example = "11988887777")
        String telefoneCliente
) {}
