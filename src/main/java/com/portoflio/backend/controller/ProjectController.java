package com.portoflio.backend.controller;

import com.portoflio.backend.dto.request.ProjectRequest;
import com.portoflio.backend.dto.response.ProjectResponse;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.exception.model.ProjectNotFoundException;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/add-to/{id}")
    public ResponseEntity<UserPortfolioResponse> addProject(@PathVariable Long id, @Valid @RequestBody ProjectRequest projectRequest) throws UserNotFoundException {
        UserPortfolioResponse updateUser = projectService.addProject(id, projectRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updateUser);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequest projectRequest) throws ProjectNotFoundException {
        ProjectResponse updateProject = projectService.updateProject(id, projectRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updateProject);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) throws ProjectNotFoundException {
        projectService.deleteProject(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
