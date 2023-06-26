package com.portoflio.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
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

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST,
            targetEntity = Role.class
    )
    @JoinTable(
            name = "user_roles",
            joinColumns =
                @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
