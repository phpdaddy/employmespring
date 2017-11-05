package com.phpdaddy.employmespring;

import com.phpdaddy.employmespring.model.Candidate;
import com.phpdaddy.employmespring.model.Employer;
import com.phpdaddy.employmespring.model.User;
import com.phpdaddy.employmespring.model.Vacancy;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Component
public class DataLoader implements ApplicationRunner {

    private EntityManagerFactory emf;

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void run(ApplicationArguments args) {
        EntityManager entityManager = this.emf.createEntityManager();

        entityManager.getTransaction().begin();
        Candidate c = new Candidate();
        c.setRole("ROLE_CANDIDATE");
        c.setUsername("candidate");
        c.setPassword("password");
        c.setEmail("candidate@example.com");
        c.setAge(40);
        c.setArea("IT");
        c.setEducation("High");
        c.setLanguages("ru");
        c.setYearsOfExperience(5);
        c.setLocation("Czech Republic");
        entityManager.persist(c);
        entityManager.flush();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Employer e = new Employer();
        e.setRole("ROLE_EMPLOYER");
        e.setUsername("employer");
        e.setPassword("password");
        e.setEmail("employer@example.com");
        e.setCompanyName("BOSCH");
        entityManager.persist(e);
        entityManager.flush();
        entityManager.getTransaction().commit();


        entityManager.getTransaction().begin();
        Vacancy v = new Vacancy();
        v.setAgeMax(50);
        v.setAgeMin(20);
        v.setArea("IT");
        v.setExperienceMin(3);
        v.setPlace("Donetsk");
        v.setName("Programmer");
        v.setDescription("Desc");
        v.setEmployer(e);
        entityManager.persist(v);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }
}
