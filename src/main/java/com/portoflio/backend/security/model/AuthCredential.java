package com.portoflio.backend.security.model;

import lombok.Data;

@Data
public class AuthCredential {
    private String email;
    private String password;
}
