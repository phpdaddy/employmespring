package com.phpdaddy.employmespring.dao;

import com.phpdaddy.employmespring.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

    Candidate findByUsername(String username);
}
