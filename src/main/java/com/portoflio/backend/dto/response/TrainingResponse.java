package com.portoflio.backend.dto.response;

import com.portoflio.backend.model.Training;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingResponse {
    private Long id;
    private String title;
    private String educationEntity;
    private boolean inProgress;
    @DateTimeFormat(pattern = "MM/yyyy")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "MM/yyyy")
    private LocalDate endDate;
    private String certificateURL;

    public TrainingResponse(Training training) {
        id = training.getId();
        title = training.getTitle();
        educationEntity = training.getEducationEntity();
        inProgress = training.isInProgress();
        startDate = training.getStartDate();
        endDate = training.getEndDate();
        certificateURL = training.getCertificateURL();
    }
}
