package com.portoflio.backend.dto.response;

import com.portoflio.backend.model.UserPortfolio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
    private List<ProjectResponse> projects;

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

        if( user.getSkills() != null){
            skills = user.getSkills().isEmpty()
                    ? null
                    : user.getSkills()
                    .stream()
                    .map(SkillResponse::new)
                    .toList();
        }

        if( user.getFormations() != null) {
            formations = user.getFormations().isEmpty()
                    ? null
                    : user.getFormations()
                    .stream()
                    .map(TrainingResponse::new)
                    .toList();
        }

        if( user.getProjects() != null ){
            projects = user.getProjects().isEmpty()
                    ? null
                    : user.getProjects()
                    .stream()
                    .map(ProjectResponse::new)
                    .toList();
        }
    }
}
