package com.portoflio.backend.model;

import com.portoflio.backend.dto.request.ProjectRequest;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 1400)
    private String description;
    @Column(name = "image_url")
    private String imageURL;
    @Column(name = "repository_url")
    private String repositoryURL;
    @Column(name = "deploy_url")
    private String deployURL;
    @ManyToOne
    private UserPortfolio userPortfolio;

    public Project(ProjectRequest project){
        title = project.getTitle();
        description = project.getDescription();
        imageURL = project.getImageURL();
        repositoryURL = project.getRepositoryURL();
        deployURL = project.getDeployURL();
    }
}
