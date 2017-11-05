package com.phpdaddy.employmespring.controller;

import com.phpdaddy.employmespring.dao.CandidateRepository;
import com.phpdaddy.employmespring.dao.EmployerRepository;
import com.phpdaddy.employmespring.model.Candidate;
import com.phpdaddy.employmespring.model.Employer;
import com.phpdaddy.employmespring.service.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collection;

@Controller
public class ProfileController {
    private final CandidateRepository candidateRepository;
    private final EmployerRepository employerRepository;

    @Autowired
    public ProfileController(CandidateRepository candidateRepository, EmployerRepository employerRepository) {
        this.candidateRepository = candidateRepository;
        this.employerRepository = employerRepository;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profileAction() {
        Collection<SimpleGrantedAuthority> authorities
                = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (authorities.stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_CANDIDATE"))) {
            return "redirect:/profile/candidate";
        } else if (authorities.stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_EMPLOYER"))) {
            return "redirect:/profile/employer";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/profile/candidate", method = RequestMethod.GET)
    public ModelAndView profileCandidatePage() {
        MyUserPrincipal myUserPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Candidate candidate = candidateRepository.findOne(myUserPrincipal.getUser().getId());
        return new ModelAndView("profile-candidate").addObject(candidate);
    }

    @RequestMapping(value = "/profile/candidate", method = RequestMethod.POST)
    public ModelAndView profileCandidate(@Valid @ModelAttribute Candidate candidate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("profile-candidate");
        }
        candidateRepository.save(candidate);
        return new ModelAndView("redirect:/profile/candidate").addObject("success", true);
    }

    @RequestMapping(value = "/profile/employer", method = RequestMethod.GET)
    public ModelAndView profileEmployerPage() {
        Employer employer = employerRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        return new ModelAndView("profile-employer").addObject(employer);
    }

    @RequestMapping(value = "/profile/employer", method = RequestMethod.POST)
    public ModelAndView profileEmployer(@Valid @ModelAttribute Employer employer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("profile-employer");
        }
        employerRepository.save(employer);
        return new ModelAndView("redirect:/profile/employer").addObject("success", true);
    }
}
