package com.portoflio.backend.model;

import com.portoflio.backend.dto.request.SkillRequest;
import com.portoflio.backend.model.enums.TypeSkill;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private TypeSkill typeSkill;
    @ManyToOne
    private UserPortfolio userPortfolio;

    public Skill(SkillRequest skill) {
        title = skill.getTitle();
        typeSkill = TypeSkill.valueOf(skill.getTypeSkill());
    }
}
