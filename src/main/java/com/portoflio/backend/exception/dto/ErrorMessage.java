package com.portoflio.backend.exception.dto;

import org.springframework.http.HttpStatus;

public record ErrorMessage(
        HttpStatus status,
        int code,
        String message
){}
