package com.phpdaddy.employmespring.controller;

import com.phpdaddy.employmespring.dao.CandidateRepository;
import com.phpdaddy.employmespring.dao.EmployerRepository;
import com.phpdaddy.employmespring.model.Candidate;
import com.phpdaddy.employmespring.model.Employer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

@Controller
public class RegisterController {
    private final CandidateRepository candidateRepository;
    private final EmployerRepository employerRepository;

    @Autowired
    public RegisterController(CandidateRepository candidateRepository, EmployerRepository employerRepository) {
        this.candidateRepository = candidateRepository;
        this.employerRepository = employerRepository;
    }

    @RequestMapping(value = "/register/candidate", method = RequestMethod.GET)
    public String registerCandidatePage(HttpServletRequest request, @ModelAttribute Candidate candidate) {
        return "register-candidate";
    }

    @RequestMapping(value = "/register/candidate", method = RequestMethod.POST)
    public ModelAndView registerCandidate(HttpServletRequest request, @Valid @ModelAttribute Candidate candidate, BindingResult bindingResult,
                                          @RequestParam(name = "repassword") String repassword) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("register-candidate");
        }
        if (!repassword.equals(candidate.getPassword())) {
            return new ModelAndView("register-candidate").addObject("passDoNotMatch", true);
        }
        candidate.setRole("ROLE_CANDIDATE");
        candidateRepository.save(candidate);
        return new ModelAndView("redirect:/register/candidate").addObject("success", true);
    }

    @RequestMapping(value = "/register/employer", method = RequestMethod.GET)
    public String registerEmployerPage(HttpServletRequest request, @ModelAttribute Employer employer) {
        return "register-employer";
    }

    @RequestMapping(value = "/register/employer", method = RequestMethod.POST)
    public ModelAndView registerEmployer(HttpServletRequest request, @Valid @ModelAttribute Employer employer, BindingResult bindingResult,
                                         @RequestParam(name = "repassword") String repassword) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("register-employer");
        }
        if (!repassword.equals(employer.getPassword())) {
            return new ModelAndView("register-employer").addObject("passDoNotMatch", true);
        }
        employer.setRole("ROLE_EMPLOYER");
        employerRepository.save(employer);
        return new ModelAndView("redirect:/register/employer").addObject("success", true);
    }
}
