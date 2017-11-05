package com.phpdaddy.employmespring.model;


import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employer")
@PrimaryKeyJoinColumn
public class Employer extends User {
    @NotBlank
    @Column(nullable = false)
    private String companyName;

    @OneToMany(mappedBy = "employer")
    private List<Vacancy> vacancies = new ArrayList<>();

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }
}

