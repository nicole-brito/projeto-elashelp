package com.soulcode.elashelp.Services;

import com.soulcode.elashelp.Models.Login;
import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Repositories.LoginRepository;
import com.soulcode.elashelp.Repositories.UsuarioRepository;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class RedefinirSenhaService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private EmailService emailService;

    public void enviarNotificacao(String email) throws Exception {
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

        //Ciação de sessão
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(minhaContaDeEmail, senha);
            }
        });

        //Prepara o envio de mensagens
        Message message = prepararNotificacao(session, minhaContaDeEmail, email);

        //Send mail
        Transport.send(message);
        System.out.println("Mensagem enviada com sucesso");
    }

    //envio de email para redefinir senha
    private Message prepararNotificacao(Session session, String minhaContaDeEmail, String email) {

//        Login user = loginRepository.findByEmail(email);
//        if (user != null) {
//            String token = UUID.randomUUID().toString();
//            user.setResetToken(token);
//            loginRepository.save(user);
//
//            try {
//                Message message = new MimeMessage(session);
//                message.setFrom(new InternetAddress(minhaContaDeEmail));
//                message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
//                message.setSubject("KittyHelp - redefinição de senha");
//                message.setText("Para redefinir sua senha, acesse o link: http://www.localhost:8080/redefinirsenha e digite o token no campo solicitante: " + token);
//
//                return message;
//            } catch (Exception ex) {
//
//                Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return null;
//        }

        return null;
    }

    //metodo pra verificar token e atualizar senha no banco
    public boolean redefinicaoSenha(String token, String senha) {
        Login user = loginRepository.findByResetToken(token);


        if (user != null && token.equals(user.getResetToken())) {
            user.setSenha(senha);//atualiza senha na tabela de login
            user.setResetToken(null); // Limpar o token após a senha ser atualizada
            loginRepository.save(user);
            return true;

        }

        return false;
    }
}

