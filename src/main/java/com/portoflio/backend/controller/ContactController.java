package com.portoflio.backend.controller;

import com.portoflio.backend.dto.request.ContactForm;
import com.portoflio.backend.exception.model.EmailSendingException;
import com.portoflio.backend.service.ContactService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/contact")
@AllArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<?> sendMailToContact(@Valid @RequestBody ContactForm contactForm) throws EmailSendingException {
        contactService.sendMailToContact(contactForm);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
