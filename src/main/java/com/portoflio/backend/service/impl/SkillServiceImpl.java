package com.portoflio.backend.service.impl;

import com.portoflio.backend.dto.request.SkillRequest;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.exception.model.ArgumentInvalidException;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.model.Skill;
import com.portoflio.backend.model.UserPortfolio;
import com.portoflio.backend.model.enums.TypeSkill;
import com.portoflio.backend.repository.SkillRepository;
import com.portoflio.backend.repository.UserPortfolioRepository;
import com.portoflio.backend.service.SkillService;
import org.springframework.stereotype.Service;

@Service
public class SkillServiceImpl implements SkillService {

    private final UserPortfolioRepository userPortfolioRepository;
    private final SkillRepository skillRepository;

    public SkillServiceImpl(UserPortfolioRepository userPortfolioRepository, SkillRepository skillRepository) {
        this.userPortfolioRepository = userPortfolioRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public UserPortfolioResponse addSkill(Long id, SkillRequest skill) throws UserNotFoundException, ArgumentInvalidException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));

        Skill newSkill = skillRepository.save(Skill.builder()
                .title(skill.getTitle())
                .typeSkill(TypeSkill.valueOf(skill.getTypeSkill()))
                .build());

        userDB.addSkill(newSkill);
        return new UserPortfolioResponse(userPortfolioRepository.save(userDB));
    }
}
