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
public class SkillRequest {
    @NotNull(message = "El titulo es obligatorio.")
    @NotEmpty(message = "El titulo es obligatorio.")
    private String title;
    @NotNull(message = "El tipo es obligatorio.")
    @NotEmpty(message = "El tipo es obligatorio.")
    private String typeSkill;
}
