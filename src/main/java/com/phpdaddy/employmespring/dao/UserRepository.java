package com.phpdaddy.employmespring.dao;

import com.phpdaddy.employmespring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
