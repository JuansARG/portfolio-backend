package com.portoflio.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Set<String> hardSkills;
    private Set<String> softSkills;
}
