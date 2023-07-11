package com.portoflio.backend.controller;

import com.portoflio.backend.dto.request.SkillRequest;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.exception.model.ArgumentInvalidException;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }


    @PatchMapping("/add-to/{id}")
    public ResponseEntity<UserPortfolioResponse> addSkill(@PathVariable Long id, @Valid @RequestBody SkillRequest skill) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolioResponse updatedUser = skillService.addSkill(id, skill);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }
}
