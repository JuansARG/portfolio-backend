package com.portoflio.backend.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {
    @NotNull(message = "La contraseña es obligatorio.")
    @NotEmpty(message = "La contraseña es obligatorio.")
    private String password;
    @NotNull(message = "La nueva contraseña es obligatoria.")
    @NotEmpty(message = "La nueva contraseña es obligatoria.")
    @Size(min = 4, max = 20, message = "La contraseña debe tener entre 4 y 20 caracteres.")
    private String newPassword;
}
