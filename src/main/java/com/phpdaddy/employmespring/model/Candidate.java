package com.phpdaddy.employmespring.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "candidate")
@PrimaryKeyJoinColumn
public class Candidate extends User {
    @Column(nullable = false)
    private int age;
    @Column(nullable = false)
    private int yearsOfExperience;
    @NotBlank
    @Column(nullable = false)
    private String education;
    @NotBlank
    @Column(nullable = false)
    private String area;
    @NotBlank
    @Column(nullable = false)
    private String languages;
    @NotBlank
    @Column(nullable = false)
    private String location;
    @ManyToMany
    @JoinTable(name = "favorite_vacancies",
            joinColumns =
            @JoinColumn(name = "vacancy_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    )
    private List<Vacancy> favoriteVacancies = new ArrayList<>();

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Vacancy> getFavoriteVacancies() {
        return favoriteVacancies;
    }
}
