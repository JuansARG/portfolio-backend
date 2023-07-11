package com.portoflio.backend.service;

import com.portoflio.backend.dto.request.SkillRequest;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.exception.model.ArgumentInvalidException;
import com.portoflio.backend.exception.model.UserNotFoundException;

public interface SkillService {

    UserPortfolioResponse addSkill(Long id, SkillRequest skill) throws UserNotFoundException, ArgumentInvalidException;

}
