package com.portoflio.backend.dto.response;

import com.portoflio.backend.model.Skill;
import com.portoflio.backend.model.enums.TypeSkill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillResponse {

    private Long id;
    private String title;
    private String typeSkill;

    public SkillResponse(Skill skill) {
        id = skill.getId();
        title = skill.getTitle();
        typeSkill = skill.getTypeSkill().name();
    }
}
