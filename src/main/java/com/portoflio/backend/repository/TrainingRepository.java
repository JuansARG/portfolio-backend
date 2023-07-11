package com.portoflio.backend.repository;

import com.portoflio.backend.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TrainingRepository extends JpaRepository<Training, Long> {
}
