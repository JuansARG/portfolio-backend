package com.portoflio.backend.repository;

import com.portoflio.backend.model.UserPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserPortfolioRepository extends JpaRepository<UserPortfolio, Long>{

    Optional<UserPortfolio> findByEmail(String email);
    boolean existsByEmail(String email);
}
