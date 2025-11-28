package com.comy_delivery_back.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para realização do pagamento no Mercado Pago")
public record PaymentResponseDTO(
        @Schema(description = "ID da preferência gerada", example = "123456789-abc")
        String preferenceId,

        @Schema(description = "URL para redirecionamento (Checkout Pro)", example = "https://www.mercadopago.com.br/checkout/v1/redirect?pref_id=...")
        String initPoint,

        @Schema(description = "URL para redirecionamento em Sandbox (Testes)", example = "https://sandbox.mercadopago.com.br/checkout/v1/redirect?pref_id=...")
        String sandboxInitPoint) {

}
