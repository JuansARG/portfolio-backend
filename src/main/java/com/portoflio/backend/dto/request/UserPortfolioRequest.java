package com.portoflio.backend.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPortfolioRequest {

    @NotNull(message = "El nombre es obligatorio.")
    @NotEmpty(message = "El nombre es obligatorio.")
    private String name;
    @NotNull(message = "El apellido es obligatorio.")
    @NotEmpty(message = "El apellido es obligatorio.")
    private String surname;
    private int age;
    private String city;
    @NotNull(message = "El email es obligatorio")
    @NotEmpty(message = "El email es obligatorio")
    private String email;
    @Size(min = 4, max = 20, message = "La contraseña debe tener entre 4 y 20 caracteres.")
    private String password;
    private String title;
    @Size(max = 1400, message = "La descripción es demasiado larga.")
    private String profile;
    private String imageURL;
    @NotEmpty
    private Set<String> hardSkills;
    @NotEmpty
    private Set<String> softSkills;
}
