package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.WebhookDTO;
import com.comy_delivery_back.dto.response.PaymentResponseDTO;
import com.comy_delivery_back.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagamento")
@Tag(name = "Pagamento", description = "Endpoints para integração com Mercado Pago")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/checkout/{pedidoId}")
    @Operation(summary = "Gerar link de pagamento", description = "Cria uma preferência de pagamento no Mercado Pago para o pedido informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Link gerado com sucesso",
                    content = @Content(schema = @Schema(implementation = PaymentResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro na geração do pagamento")
    })
    public ResponseEntity<PaymentResponseDTO> checkout(
            @Parameter(description = "ID do pedido", example = "1", required = true)
            @PathVariable Long pedidoId) {
        PaymentResponseDTO response = paymentService.criarPreferenciaPagamento(pedidoId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/webhook")
    @Operation(summary = "Webhook Mercado Pago", description = "Endpoint para receber notificações de atualização de status de pagamento.")
    @ApiResponse(responseCode = "200", description = "Notificação recebida")
    public ResponseEntity<Void> webhook(@RequestBody WebhookDTO webhookDTO) {
        paymentService.processarWebhook(webhookDTO);
        return ResponseEntity.ok().build();
    }
}
