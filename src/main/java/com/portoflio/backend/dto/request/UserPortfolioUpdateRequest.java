package com.portoflio.backend.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPortfolioUpdateRequest {
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
    private String title;
    @Size(max = 1400, message = "La descripción es demasiado larga.")
    private String profile;
    private String imageURL;
}
