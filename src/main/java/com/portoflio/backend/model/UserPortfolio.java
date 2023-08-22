package com.portoflio.backend.model;

import com.portoflio.backend.dto.request.UserPortfolioRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class UserPortfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean verified;
    private String name;
    private String surname;
    private int age;
    private String city;
    private String email;
    private String password;
    private String title;
    @Column(length = 1400)
    private String profile;
    @Column(name = "imagen_url")
    private String imageURL;

    @OneToMany(
            mappedBy = "userPortfolio",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @Builder.Default
    private Set<Skill> skills = new HashSet<>();

    @Column(name = "password_reset_code")
    private String passwordResetCode;

    @OneToMany(
            mappedBy = "userPortfolio",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @OneToMany(
            mappedBy = "userPortfolio",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @Builder.Default
    private Set<Training> formations = new HashSet<>();

    @OneToMany(
            mappedBy = "userPortfolio",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @Builder.Default
    private Set<Project> projects = new HashSet<>();

    public UserPortfolio(UserPortfolioRequest user) {
        name = user.getName();
        surname = user.getSurname();
        age = user.getAge();
        city = user.getCity();
        email = user.getEmail();
        title = user.getTitle();
        profile = user.getProfile();
        imageURL = user.getImageURL();

        if(!user.getSkills().isEmpty()){
            skills = user.getSkills()
                    .stream()
                    .map(Skill::new)
                    .collect(Collectors.toSet());
        }

        if(!user.getFormations().isEmpty()){
            formations = user.getFormations()
                    .stream()
                    .map( t -> {
                        Training newTraining = new Training(t);
                        newTraining.setUserPortfolio(this);
                        return newTraining;
                    })
                    .collect(Collectors.toSet());
        }

        if(!user.getProjects().isEmpty()){
            projects = user.getProjects()
                    .stream()
                    .map( p -> {
                        Project newProject = new Project(p);
                        newProject.setUserPortfolio(this);
                        return newProject;
                    })
                    .collect(Collectors.toSet());
        }
    }

    public void addSkill(Skill skill){
        skills.add(skill);
    }
    public void addTraining(Training training){ formations.add(training); }
}
