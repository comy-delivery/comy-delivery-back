package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.WebhookDTO;
import com.comy_delivery_back.dto.response.PaymentResponseDTO;
import com.comy_delivery_back.enums.StatusPedido;
import com.comy_delivery_back.exception.PedidoNaoEncontradoException;
import com.comy_delivery_back.exception.RegraDeNegocioException;
import com.comy_delivery_back.model.Pedido;
import com.comy_delivery_back.repository.PedidoRepository;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PaymentService {

    private final PedidoRepository pedidoRepository;

    @Value("${app.backend.url}")
    private String backendUrl;

    @Value("${app.frontend.url}") // Para redirecionar o usuário após o pagamento
    private String frontendUrl;

    public PaymentService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional(readOnly = true)
    public PaymentResponseDTO criarPreferenciaPagamento(Long pedidoId) {
        log.info("Iniciando criação de preferência de pagamento para o pedido {}", pedidoId);

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));

        if (pedido.getStatus() == StatusPedido.CANCELADO || pedido.getStatus() == StatusPedido.ENTREGUE) {
            throw new RegraDeNegocioException("Não é possível gerar pagamento para um pedido cancelado ou entregue.");
        }

        try {
            // 1. Criar itens da preferência
            List<PreferenceItemRequest> items = new ArrayList<>();

            // Adiciona um item genérico representando o total do pedido (simplificação para evitar problemas com decimais nos itens individuais + frete + desconto)
            // Se preferir detalhado, itere sobre pedido.getItensPedido()
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title("Pedido #" + pedido.getIdPedido() + " - " + pedido.getRestaurante().getNmRestaurante())
                    .quantity(1)
                    .unitPrice(pedido.getVlTotal())
                    .currencyId("BRL")
                    .build();
            items.add(itemRequest);

            // 2. Configurar urls de retorno
            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success(frontendUrl + "/pedido/sucesso")
                    .pending(frontendUrl + "/pedido/pendente")
                    .failure(frontendUrl + "/pedido/falha")
                    .build();

            // 3. Configurar Payer (Pagador)
            PreferencePayerRequest payer = PreferencePayerRequest.builder()
                    .name(pedido.getCliente().getNmCliente())
                    .email(pedido.getCliente().getEmailCliente())
                    .build();

            // 4. Montar requisição da preferência
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .payer(payer)
                    .backUrls(backUrls)
                    .autoReturn("approved")
                    .externalReference(pedido.getIdPedido().toString()) // VINCULO IMPORTANTE: ID DO PEDIDO
//                    .notificationUrl(backendUrl + "/api/pagamento/webhook") // URL que o MP vai chamar
                    .build();

            // 5. Chamada ao SDK
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            log.info("Preferência criada com sucesso: {}", preference.getId());

            return new PaymentResponseDTO(
                    preference.getId(),
                    preference.getInitPoint(),
                    preference.getSandboxInitPoint()
            );

        } catch (MPException | MPApiException e) {


            log.error("Erro ao criar preferência no Mercado Pago: {}", e.getMessage(), e);
            throw new RegraDeNegocioException("Erro ao gerar link de pagamento.");
        }
    }

    @Transactional
    public void processarWebhook(WebhookDTO webhookDTO) {
        if (webhookDTO.type() != null && webhookDTO.type().equals("payment")) {
            String paymentId = webhookDTO.data().id();
            log.info("Recebido webhook de pagamento. ID: {}", paymentId);

            try {
                PaymentClient client = new PaymentClient();
                Payment payment = client.get(Long.parseLong(paymentId));

                if ("approved".equals(payment.getStatus())) {
                    Long pedidoId = Long.parseLong(payment.getExternalReference());
                    confirmarPagamentoPedido(pedidoId);
                } else {
                    log.warn("Pagamento {} recebido mas status é: {}", paymentId, payment.getStatus());
                }

            } catch (MPException | MPApiException e) {
                log.error("Erro ao buscar status do pagamento {}: {}", paymentId, e.getMessage());
            } catch (NumberFormatException e) {
                log.error("External Reference inválida recebida no webhook");
            }
        }
    }

    private void confirmarPagamentoPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));

        log.info("Pagamento aprovado para o pedido {}. Atualizando status.", pedidoId);

        // Lógica de negócio: Se o pedido estava PENDENTE, agora está CONFIRMADO (ou em status específico de PAGO se houver)
        if (pedido.getStatus() == StatusPedido.PENDENTE) {
            pedido.setStatus(StatusPedido.CONFIRMADO);
            pedido.setDtAtualizacao(LocalDateTime.now());
            // Aqui pode adicionar lógica para notificar o restaurante, etc.
            pedidoRepository.save(pedido);
        }
    }
}
