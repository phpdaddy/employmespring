package com.phpdaddy.employmespring.dao;

import com.phpdaddy.employmespring.model.Candidate;
import com.phpdaddy.employmespring.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<Employer, Integer> {

    Employer findByUsername(String username);
}
