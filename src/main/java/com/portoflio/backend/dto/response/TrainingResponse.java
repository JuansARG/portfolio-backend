package com.portoflio.backend.dto.response;

import com.portoflio.backend.model.Training;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingResponse {
    private Long id;
    private String title;
    private String educationEntity;
    private boolean inProgress;
    private String startDate;
    private String endDate;
    private String certificateURL;

    public TrainingResponse(Training training) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        id = training.getId();
        title = training.getTitle();
        educationEntity = training.getEducationEntity();
        inProgress = training.isInProgress();
        startDate = training.getStartDate().format(formatter);
        endDate = training.getEndDate().format(formatter);
        certificateURL = training.getCertificateURL();
    }
}
