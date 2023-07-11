package com.portoflio.backend.repository;

import com.portoflio.backend.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SkillRepository extends JpaRepository<Skill, Long> {
}
