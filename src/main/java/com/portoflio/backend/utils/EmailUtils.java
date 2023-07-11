package com.portoflio.backend.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.ContentType;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.swing.text.AbstractDocument;


@Service
public class EmailUtils {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromMail;

    public void sendMessageToVerify(String email, Long id){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(email);
        message.setSubject("Verificación de cuenta.");
        message.setText("Por favor, verifica tu cuenta haciendo clic en el siguiente enlace: http://localhost:8080/api/v1/auth/".concat(String.valueOf(id)).concat("/verify-account"));

        javaMailSender.send(message);
    }

    public void sendConfirmationMessage(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(email);
        message.setSubject("Verificación exitosa.");
        message.setText("La cuenta con email ".concat(email).concat(" ha sido verificada con éxito."));

        javaMailSender.send(message);
    }

    public void sendPasswordChangeNotification(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(email);
        message.setSubject("Cambio de contraseña exitoso.");
        message.setText("La contraseña de la cuenta ".concat(email).concat(" ha sido actualizada."));

        javaMailSender.send(message);
    }

    // Este método no sirve porque la password obtenida de la BD no se puede desencriptar
    public void sendPassword(String email, String password) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromMail);
        helper.setTo(email);
        helper.setSubject("Recuperar contraseña.");

        String htmlMessage = "<p><b>Detalles para inicio de session</b></p>" +
                "<br>" +
                "<p><b>Email: </b>" + email + "</p>" +
                "<br>" +
                "<p><b>Password: </b>" + password + "</p>";

        message.setContent(htmlMessage, "text/html");

        javaMailSender.send(message);
    }

    public void sendLinkToResetPassword(String email, String code) {
        String link = "http://localhost:8080/api/v1/auth/password-reset?email=" + email + "&code=" + code;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(email);
        message.setSubject("Solicitud para restablecer contraseña.");
        message.setText("Para restablecer la contraseña, realice una petición post al siguiente link: " + link +
                        " que debera contener en su body una propiedad llamada newPassword...");

        javaMailSender.send(message);
    }
}
