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
public class TrainingRequest {
    @NotNull(message = "El titulo es obligatorio.")
    @NotEmpty(message = "El titulo es obligatorio.")
    private String title;
    @NotNull(message = "El nombre de la institution es obligatorio.")
    @NotEmpty(message = "El nombre de la institution es obligatorio.")
    private String educationEntity;
    private boolean inProgress;
    private String startDate;
    private String endDate;
    private String certificateURL;

}
