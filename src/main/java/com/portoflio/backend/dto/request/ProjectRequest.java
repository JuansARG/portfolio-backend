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
public class ProjectRequest {
    @NotNull(message = "El titulo es obligatorio.")
    @NotEmpty(message = "El titulo es obligatorio.")
    private String title;
    @NotNull(message = "La descripción es obligatoria.")
    @NotEmpty(message = "La descripción es obligatoria.")
    private String description;
    @NotNull(message = "La imagen es obligatoria.")
    @NotEmpty(message = "La imagen es obligatoria.")
    private String imageURL;
    private String repositoryURL;
    private String deployURL;
}
