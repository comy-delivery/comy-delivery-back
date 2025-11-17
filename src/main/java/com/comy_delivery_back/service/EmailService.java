package com.comy_delivery_back.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${EMAIL_SENDER}")
    private String remetente;

    public void enviarEmail(String destinatario, String assunto, String corpo){
        SimpleMailMessage mensagem = new SimpleMailMessage();

        mensagem.setFrom(remetente);
        mensagem.setTo(destinatario);
        mensagem.setSubject(assunto);
        mensagem.setText(corpo);

        try{
            javaMailSender.send(mensagem);
            System.out.println("E-mail enviado com sucesso para: " + destinatario);
        }catch (Exception e){
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
            throw new RuntimeException("Falha no serviço de envio de e-mail.", e);
        }



    }

    public void enviarEmailRecuperacao(String destinatario, String linkRecuperacao){
        String assunto = "Comy Delivery - Redefinação de senha";
        String corpo = String.format(
                "Você solicitou a redefinição de sua senha.\n\n" +
                        "Clique no link abaixo para criar uma nova senha. Este link expira em 30 minutos:\n" +
                        "%s\n\n" +
                        "Se você não solicitou esta redefinição, por favor, ignore este e-mail.",
                linkRecuperacao
        );

        enviarEmail(destinatario, assunto, corpo);

    }
}
