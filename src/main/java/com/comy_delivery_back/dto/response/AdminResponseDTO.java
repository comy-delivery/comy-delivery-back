package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.model.Admin;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta contendo dados de um administrador")
public record AdminResponseDTO(
        @Schema(description = "ID do administrador", example = "1")
        Long id,

        @Schema(description = "Nome de usuário", example = "admin_master")
        String username,

        @Schema(description = "Nome completo", example = "Administrador Geral")
        String nmAdmin,

        @Schema(description = "CPF", example = "11122233344")
        String cpfAdmin,

        @Schema(description = "Email", example = "admin@comy.com")
        String emailAdmin,

        @Schema(description = "Status de ativo", example = "true")
        boolean isAtivo
) {
    public AdminResponseDTO(Admin a){
        this(
                a.getId(),
                a.getUsername(),
                a.getNmAdmin(),
                a.getCpfAdmin(),
                a.getEmailAdmin(),
                a.isAtivo()
        );
    }
}
