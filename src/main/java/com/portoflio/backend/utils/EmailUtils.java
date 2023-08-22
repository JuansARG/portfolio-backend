package com.portoflio.backend.utils;

import com.portoflio.backend.dto.request.ContactForm;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailUtils {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromMail;

    @Value("${spring.url.dev}")
    private String URL_DEV;

    public void sendMessageToVerify(String email, Long id){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(email);
        message.setSubject("Verificación de cuenta.");
        message.setText(String.format("Por favor, verifica tu cuenta haciendo una petición post al siguiente enlace: %s/api/v1/auth/%d/verify-account", URL_DEV, id));
        javaMailSender.send(message);
    }

    public void sendConfirmationMessage(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(email);
        message.setSubject("Verificación exitosa.");
        message.setText(String.format("La cuenta con email %s ha sido verificada con éxito.", email));

        javaMailSender.send(message);
    }

    public void sendPasswordChangeNotification(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(email);
        message.setSubject("Cambio de contraseña exitoso.");
        message.setText(String.format("La contraseña de la cuenta %s ha sido actualizada.", email));

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
        String linkFormat = String.format("%s/api/v1/auth/password-reset?email=%s&code=%s", URL_DEV, email, code);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(email);
        message.setSubject("Solicitud para restablecer contraseña.");
        message.setText(String.format("Para restablecer la contraseña, realice una petición post al siguiente link: %s " +
                "que debera contener en su body una propiedad llamada newPassword...", linkFormat));

        javaMailSender.send(message);
    }

    public void automaticResponseEmail(ContactForm contactForm){
        javaMailSender.send(mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(fromMail);
            helper.setTo(contactForm.getEmail());
            helper.setSubject("Respuesta Automática - Gracias por ponerte en contacto conmigo!");
            helper.setText(String.format("""
                                <p>Estimado/a <b>%s</b>,</p>
                                <p>He recibido su mensaje y le agradezco su interés en mi servicios.</p>
                                <p>Revisare su mensaje y me pondré en contacto con usted lo antes posible.</p>
                                <p>¡Gracias nuevamente por su interés en mí!</p>
                                <p>Saludos cordiales,</p>
                                <p>Juan Ignacio Sarmiento</p>""", contactForm.getName()), true);
        });
    }

    public void sendMailToContact(ContactForm contactForm) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(fromMail);
        message.setSubject(String.format("Mensaje de: <%s> Asunto: %s", contactForm.getEmail(), contactForm.getSubject()));
        message.setText(contactForm.getMessage());
        message.setCc();

        javaMailSender.send(message);
        automaticResponseEmail(contactForm);
    }
}
