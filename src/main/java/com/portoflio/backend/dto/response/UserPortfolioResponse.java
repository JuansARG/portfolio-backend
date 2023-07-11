package com.portoflio.backend.dto.response;

import com.portoflio.backend.model.Skill;
import com.portoflio.backend.model.UserPortfolio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPortfolioResponse {
    private Long id;
    private String name;
    private String surname;
    private int age;
    private String city;
    private String email;
    private String title;
    private String profile;
    private String imageURL;
    private List<SkillResponse> skills;
    private List<TrainingResponse> formations;

    public UserPortfolioResponse(UserPortfolio user) {
        id = user.getId();
        name = user.getName();
        surname = user.getSurname();
        age = user.getAge();
        city = user.getCity();
        email = user.getEmail();
        title = user.getTitle();
        profile = user.getProfile();
        imageURL = user.getImageURL();
        skills = user.getSkills()
                .stream()
                .map(SkillResponse::new)
                .toList();
        formations = user.getFormations()
                .stream()
                .map(TrainingResponse::new)
                .toList();
    }
}
