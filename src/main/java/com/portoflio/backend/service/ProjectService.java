package com.portoflio.backend.service;

import com.portoflio.backend.dto.request.ProjectRequest;
import com.portoflio.backend.dto.response.ProjectResponse;
import com.portoflio.backend.exception.model.ProjectNotFoundException;
import com.portoflio.backend.exception.model.UserNotFoundException;

public interface ProjectService {
    ProjectResponse addProject(Long id, ProjectRequest projectRequest) throws UserNotFoundException;

    ProjectResponse updateProject(Long id, ProjectRequest projectRequest) throws ProjectNotFoundException;

    void deleteProject(Long idProject, Long idUser) throws ProjectNotFoundException, UserNotFoundException;
}
