package com.comy_delivery_back.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para alteração de senha")
public record AtualizarSenhaRequestDTO(
        @Schema(description = "Senha atual", example = "SenhaForte123", required = true)
        String senhaAntiga,

        @Schema(description = "Nova senha", example = "NovaSenha456", required = true)
        String novaSenha
) {}
