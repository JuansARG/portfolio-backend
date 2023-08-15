package com.portoflio.backend.service;

import com.portoflio.backend.dto.request.ContactForm;
import com.portoflio.backend.exception.model.EmailSendingException;

public interface ContactService {

    void sendMailToContact(ContactForm contactForm) throws EmailSendingException;
}
