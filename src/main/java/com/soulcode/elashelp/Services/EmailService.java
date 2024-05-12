package com.soulcode.elashelp.Services;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmailService {


    public static void sendEmail(String destino) throws Exception {
        System.out.println("Preparando para enviar o email");
        Properties properties = new Properties();

        //Autenticação
        properties.put("mail.smtp.auth", "true");
        //TLS
        properties.put("mail.smtp.starttls.enable", "true");
        //SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //SMT port
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "*");


        //Endereço de email
        String minhaContaDeEmail = "kittyhelp16@gmail.com";
        //Senha de email
        String senha = "pyce kgzg aoii bpfe";

        //Criação de sessão
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(minhaContaDeEmail, senha);
            }
        });

        //Prepara o envio de mensagens
        Message message = prepararMensagem(session, minhaContaDeEmail, destino);

        //Send mail
        Transport.send(message);
        System.out.println("Mensagem enviada com sucesso");
    }


    //Envio de email de cadastro efetuado
    private static Message prepararMensagem(Session session, String minhaContaDeEmail, String destino) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(minhaContaDeEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            message.setSubject("KittyHelp cadastrou");
            message.setText("Olá, obrigada por se cadastrar no nosso sistema, você acaba de dar um grande passo em seus problemas de TI diarios, pra que sofrimento, faz um KittyHelp!!!");

            return message;
        } catch (Exception ex) {

            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }



}

