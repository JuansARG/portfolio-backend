package com.portoflio.backend.service.impl;

import com.portoflio.backend.dto.request.ContactForm;
import com.portoflio.backend.exception.model.EmailSendingException;
import com.portoflio.backend.service.ContactService;
import com.portoflio.backend.utils.EmailUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final EmailUtils emailUtils;
    @Override
    public void sendMailToContact(ContactForm contactForm) throws EmailSendingException {
        try {
            emailUtils.sendMailToContact(contactForm);
        }catch (Exception e){
            throw new EmailSendingException("Algo salió mal intentando enviar los emails...");
        }
    }
}
