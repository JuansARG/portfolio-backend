package com.portoflio.backend.service;

import com.portoflio.backend.dto.request.ProjectRequest;
import com.portoflio.backend.dto.response.ProjectResponse;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.exception.model.ProjectNotFoundException;
import com.portoflio.backend.exception.model.UserNotFoundException;

public interface ProjectService {
    UserPortfolioResponse addProject(Long id, ProjectRequest projectRequest) throws UserNotFoundException;

    ProjectResponse updateProject(Long id, ProjectRequest projectRequest) throws ProjectNotFoundException;

    void deleteProject(Long id) throws ProjectNotFoundException;
}
