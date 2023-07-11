package com.portoflio.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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
    private String educationEntity;
    private boolean inProgress;
    private LocalDate startDate;
    private LocalDate endDate;
    private String certificateURL;
    @ManyToOne
    private UserPortfolio userPortfolio;


}
