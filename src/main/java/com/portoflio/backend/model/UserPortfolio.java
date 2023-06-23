package com.portoflio.backend.model;

import jakarta.persistence.*;
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
@Entity
@Table(name = "users_portfolio")
public class UserPortfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private int age;
    private String city;
    private String email;
    private String password;
    private String title;
    @Column(length = 1400)
    private String profile;
    private String imageURL;

    @Column(name = "hard_skills")
    private Set<String> hardSkills;

    @Column(name = "soft_skills")
    private Set<String> softSkills;

    public void addHardSkills(List<String> skills){
        hardSkills.addAll(skills);
    }

    public void addSoftSkills(List<String> skills){
        softSkills.addAll(skills);
    }
}
