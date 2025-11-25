package com.comy_delivery_back.dto.request;

public record AtualizarSenhaRequestDTO(
        String senhaAntiga,
        String novaSenha
) {
}
