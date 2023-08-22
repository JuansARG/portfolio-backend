package com.portoflio.backend.controller;

import com.portoflio.backend.dto.request.SkillRequest;
import com.portoflio.backend.dto.response.SkillResponse;
import com.portoflio.backend.exception.model.SkillNotFoundException;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.service.SkillService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/skills")

@AllArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping("/add-to/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SkillResponse> addSkill(@PathVariable Long id, @Valid @RequestBody SkillRequest skill) throws UserNotFoundException {
        SkillResponse newSkill = skillService.addSkill(id, skill);
        return ResponseEntity.status(HttpStatus.OK).body(newSkill);
    }

    @DeleteMapping("/delete/{idSkill}/from-user/{idUser}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteSkill(@PathVariable Long idSkill, @PathVariable Long idUser) throws SkillNotFoundException, UserNotFoundException {
        skillService.deleteSkill(idSkill, idUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
