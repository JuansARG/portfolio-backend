package com.portoflio.backend.dto.response;

import com.portoflio.backend.model.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private String imageURL;
    private String repositoryURL;
    private String deployURL;

    public ProjectResponse(Project project){
        id = project.getId();
        title = project.getTitle();
        description = project.getDescription();
        imageURL = project.getImageURL();
        repositoryURL = project.getRepositoryURL();
        deployURL = project.getDeployURL();
    }
}
