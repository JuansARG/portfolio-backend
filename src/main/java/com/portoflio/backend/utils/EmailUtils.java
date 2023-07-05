package com.portoflio.backend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailUtils {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromMail;

    public void sendMessageToVerify(String to){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(to);
        message.setSubject("Verificación de cuenta");
        message.setText("Por favor, verifica tu cuenta haciendo clic en el siguiente enlace: http://localhost:8080/api/v1/auth/".concat().concat("/verify-account"));

        javaMailSender.send(message);
    }

    public void sendConfirmationMessage(String to){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(to);
        message.setSubject("Verificación exitosa.");
        message.setText("La cuenta con email ".concat(to).concat(" ha sido verificada con éxito."));

        javaMailSender.send(message);
    }

    public void sendPasswordChangeNotification(String to){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(to);
        message.setSubject("Cambio de contraseña exitoso.");
        message.setText("La contraseña de la cuenta ".concat(to).concat(" ha sido actualizada"));

        javaMailSender.send(message);
    }
}
