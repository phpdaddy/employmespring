package com.phpdaddy.employmespring.dao;

import com.phpdaddy.employmespring.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {

}
