package com.portoflio.backend.service.impl;

import com.portoflio.backend.dto.request.ProjectRequest;
import com.portoflio.backend.dto.response.ProjectResponse;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.exception.model.ProjectNotFoundException;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.model.Project;
import com.portoflio.backend.model.UserPortfolio;
import com.portoflio.backend.repository.ProjectRepository;
import com.portoflio.backend.repository.UserPortfolioRepository;
import com.portoflio.backend.service.ProjectService;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final UserPortfolioRepository userPortfolioRepository;
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(UserPortfolioRepository userPortfolioRepository, ProjectRepository projectRepository) {
        this.userPortfolioRepository = userPortfolioRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public UserPortfolioResponse addProject(Long id, ProjectRequest projectRequest) throws UserNotFoundException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));

        Project newProject = projectRepository.save(
                Project.builder()
                        .title(projectRequest.getTitle())
                        .description(projectRequest.getDescription())
                        .imageURL(projectRequest.getImageURL())
                        .repositoryURL(projectRequest.getRepositoryURL())
                        .deployURL(projectRequest.getDeployURL())
                        .userPortfolio(userDB)
                        .build()
        );

        return new UserPortfolioResponse(userDB);
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest projectRequest) throws ProjectNotFoundException {
        Project projectDB = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("No existe con proyecto con ID: " + id));

        projectDB.setTitle(projectDB.getTitle());
        projectDB.setDescription(projectRequest.getDescription());
        projectDB.setImageURL(projectRequest.getImageURL());
        projectDB.setRepositoryURL(projectRequest.getRepositoryURL());
        projectDB.setDeployURL(projectRequest.getDeployURL());

        return new ProjectResponse(projectRepository.save(projectDB));
    }

    @Override
    public void deleteProject(Long id) throws ProjectNotFoundException {
        if( !projectRepository.existsById(id) ) throw new ProjectNotFoundException("No existe un projecto con ID: " + id);
        projectRepository.deleteById(id);
    }
}
