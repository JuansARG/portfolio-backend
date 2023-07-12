package com.portoflio.backend.model;

import com.portoflio.backend.dto.request.TrainingRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "training")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "education_entity")
    private String educationEntity;
    @Column(name = "in_progress")
    private boolean inProgress;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "certificate_url")
    private String certificateURL;
    @ManyToOne
    private UserPortfolio userPortfolio;

    public Training(TrainingRequest training) {

        DateTimeFormatter formatterNEW = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        title = training.getTitle();
        educationEntity = training.getEducationEntity();
        inProgress = training.isInProgress();
        startDate = LocalDate.parse(training.getStartDate(), formatterNEW);
        endDate = LocalDate.parse(training.getEndDate(), formatterNEW);
        certificateURL = training.getCertificateURL();

    }
}
