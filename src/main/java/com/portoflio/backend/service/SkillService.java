package com.portoflio.backend.service;

import com.portoflio.backend.dto.request.SkillRequest;
import com.portoflio.backend.dto.response.SkillResponse;
import com.portoflio.backend.exception.model.SkillNotFoundException;
import com.portoflio.backend.exception.model.UserNotFoundException;

public interface SkillService {

    SkillResponse addSkill(Long id, SkillRequest skill) throws UserNotFoundException;
    void deleteSkill(Long idSkill, Long idUser) throws SkillNotFoundException, UserNotFoundException;

}
