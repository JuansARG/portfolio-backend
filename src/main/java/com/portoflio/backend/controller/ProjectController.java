package com.portoflio.backend.controller;

import com.portoflio.backend.dto.request.ProjectRequest;
import com.portoflio.backend.dto.response.ProjectResponse;
import com.portoflio.backend.exception.model.ProjectNotFoundException;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.service.ProjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/project")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/add-to/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProjectResponse> addProject(@PathVariable Long id, @Valid @RequestBody ProjectRequest projectRequest) throws UserNotFoundException {
        ProjectResponse newProject = projectService.addProject(id, projectRequest);
        return ResponseEntity.status(HttpStatus.OK).body(newProject);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequest projectRequest) throws ProjectNotFoundException {
        ProjectResponse updateProject = projectService.updateProject(id, projectRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updateProject);
    }

    @DeleteMapping("/delete/{idProject}/from-user/{idUser}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProject(@PathVariable Long idProject, @PathVariable Long idUser) throws ProjectNotFoundException, UserNotFoundException {
        projectService.deleteProject(idProject, idUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
