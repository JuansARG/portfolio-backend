package com.portoflio.backend.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactForm {
    @NotNull(message = "El nombre es obligatorio.")
    @NotEmpty(message = "El nombre es obligatorio.")
    private String name;
    @NotNull(message = "El email es obligatorio")
    @NotEmpty(message = "El email es obligatorio")
    private String email;
    @NotNull(message = "El asunto es obligatorio")
    @NotEmpty(message = "El asunto es obligatorio")
    private String subject;
    @NotNull(message = "El mensaje es obligatorio")
    @NotEmpty(message = "El mensaje es obligatorio")
    private String message;
}
