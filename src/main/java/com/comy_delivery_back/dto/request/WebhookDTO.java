package com.comy_delivery_back.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "DTO simplificado para recebimento de notificação do Mercado Pago")
public record WebhookDTO(
        @Schema(description = "ID do evento", example = "12345")
        String id,

        @Schema(description = "Tipo do evento", example = "payment")
        String type,

        @Schema(description = "Data de criação", example = "2024-01-01T12:00:00Z")
        String date_created,

        @Schema(description = "Dados do objeto (contém o ID do pagamento)", example = "{id: '123'}")
        Data data
) {
    public record Data(String id) {}
}
