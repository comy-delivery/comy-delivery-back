package com.comy_delivery_back.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${EMAIL_SENDER}")
    private String remetente;

    @Async
    public CompletableFuture<Boolean> enviarEmail(String destinatario, String assunto, String corpo){
        log.info("A iniciar envio de e-mail para: {}", destinatario);
        SimpleMailMessage mensagem = new SimpleMailMessage();

        mensagem.setFrom(remetente);
        mensagem.setTo(destinatario);
        mensagem.setSubject(assunto);
        mensagem.setText(corpo);

        try{
            javaMailSender.send(mensagem);
            log.info("E-mail enviado com sucesso para: {}", destinatario);
            return CompletableFuture.completedFuture(true);
        }catch (Exception e){
            log.error("Erro ao enviar e-mail para {}: {}", destinatario, e.getMessage(), e);
            return CompletableFuture.completedFuture(false);
        }

    }

    @Async
    public CompletableFuture<Boolean> enviarEmailRecuperacao(String destinatario, String linkRecuperacao) {
        log.debug("A preparar e-mail de recuperação para: {}", destinatario);
        String assunto = "Comy Delivery - Redefinição de senha";
        String corpo = String.format(
                "Você solicitou a redefinição de sua senha.\n\n" +
                        "Clique no link abaixo para criar uma nova senha. Este link expira em 15 minutos:\n" +
                        "%s\n\n" +
                        "Se você não solicitou esta redefinição, por favor, ignore este e-mail.\n\n" +
                        "Atenciosamente,\n" +
                        "Equipe Comy Delivery",
                linkRecuperacao
        );

        return enviarEmail(destinatario, assunto, corpo);
    }

    @Async
    public CompletableFuture<Boolean> enviarEmailConfirmacaoPedido(
            String destinatario,
            Long numeroPedido,
            String nomeRestaurante) {
        log.debug("A preparar e-mail de confirmar pedido para: {}", destinatario);
        String assunto = "Comy Delivery - Pedido Confirmado #" + numeroPedido;
        String corpo = String.format(
                "Seu pedido #%d foi confirmado!\n\n" +
                        "Restaurante: %s\n" +
                        "Status: Em preparo\n\n" +
                        "Você receberá atualizações sobre o status do seu pedido.\n\n" +
                        "Obrigado por escolher Comy Delivery!",
                numeroPedido,
                nomeRestaurante
        );

        return enviarEmail(destinatario, assunto, corpo);
    }

    @Async
    public CompletableFuture<Boolean> enviarEmailPedidoCancelado(
            String destinatario,
            Long numeroPedido,
            String motivo) {
        log.debug("A preparar e-mail de pedido cancelado para: {}", destinatario);
        String assunto = "Comy Delivery - Pedido Cancelado #" + numeroPedido;
        String corpo = String.format(
                "Infelizmente seu pedido #%d foi cancelado.\n\n" +
                        "Motivo: %s\n\n" +
                        "Se tiver dúvidas, entre em contato conosco.\n\n" +
                        "Comy Delivery",
                numeroPedido,
                motivo != null ? motivo : "Não informado"
        );

        return enviarEmail(destinatario, assunto, corpo);
    }
}
