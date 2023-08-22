package com.portoflio.backend.service.impl;

import com.portoflio.backend.dto.request.SkillRequest;
import com.portoflio.backend.dto.response.SkillResponse;
import com.portoflio.backend.exception.model.SkillNotFoundException;
import com.portoflio.backend.exception.model.UserNotFoundException;
import com.portoflio.backend.model.Skill;
import com.portoflio.backend.model.UserPortfolio;
import com.portoflio.backend.model.enums.TypeSkill;
import com.portoflio.backend.repository.SkillRepository;
import com.portoflio.backend.repository.UserPortfolioRepository;
import com.portoflio.backend.service.SkillService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final UserPortfolioRepository userPortfolioRepository;
    private final SkillRepository skillRepository;

    @Override
    public SkillResponse addSkill(Long id, SkillRequest skill) throws UserNotFoundException {
        UserPortfolio userDB = userPortfolioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con ID: " + id));

        Skill newSkill = skillRepository.save(Skill.builder()
                .title(skill.getTitle())
                .typeSkill(TypeSkill.valueOf(skill.getTypeSkill()))
                .userPortfolio(userDB)
                .build());

        return new SkillResponse(newSkill);
    }

    @Override
    public void deleteSkill(Long idSkill, Long idUser) throws SkillNotFoundException, UserNotFoundException {
        if ( !skillRepository.existsById(idSkill) ) throw new SkillNotFoundException("No existe un skill con ID: " + idSkill);

        Optional<UserPortfolio> userDB = userPortfolioRepository.findById(idUser);
        if (userDB.isEmpty()) throw new UserNotFoundException("No existe un usuario con ID: " + idUser);

        userDB.get().setSkills(
                userDB.get().getSkills()
                        .stream()
                        .filter(skill -> !skill.getId().equals(idSkill))
                        .collect(Collectors.toSet())
        );

        userPortfolioRepository.save(userDB.get());
        skillRepository.deleteById(idSkill);
    }
}
