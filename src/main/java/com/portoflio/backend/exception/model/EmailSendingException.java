package com.portoflio.backend.exception.model;

public class EmailSendingException extends Exception{
    public EmailSendingException(String message) {
        super(message);
    }
}
