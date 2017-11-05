package com.phpdaddy.employmespring.controller;

import com.phpdaddy.employmespring.dao.CandidateRepository;
import com.phpdaddy.employmespring.dao.EmployerRepository;
import com.phpdaddy.employmespring.dao.VacancyRepository;
import com.phpdaddy.employmespring.model.Candidate;
import com.phpdaddy.employmespring.model.Employer;
import com.phpdaddy.employmespring.model.User;
import com.phpdaddy.employmespring.model.Vacancy;
import com.phpdaddy.employmespring.service.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class VacancyController {
    private final VacancyRepository vacancyRepository;
    private final CandidateRepository candidateRepository;
    private final EmployerRepository employerRepository;

    @Autowired
    public VacancyController(VacancyRepository vacancyRepository, CandidateRepository candidateRepository, EmployerRepository employerRepository) {
        this.vacancyRepository = vacancyRepository;
        this.candidateRepository = candidateRepository;
        this.employerRepository = employerRepository;
    }

    @RequestMapping("/vacancies")
    public ModelAndView vacancies() {
        MyUserPrincipal myUserPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Candidate candidate = candidateRepository.findOne(myUserPrincipal.getUser().getId());
        ModelAndView modelAndView = new ModelAndView("vacancies").addObject("vacancies", this.vacancyRepository.findAll());

        if (candidate != null) {
            modelAndView.addObject("favorites", this.candidateRepository.findOne(candidate.getId()).getFavoriteVacancies());
        }
        return modelAndView;
    }

    @RequestMapping("/vacancies/{id}")
    public ModelAndView vacancyDetail(@PathVariable Integer id) {
        return new ModelAndView("vacancyDetail")
                .addObject("vacancy", this.vacancyRepository.findOne(id));
    }

    @RequestMapping(path = "/vacancies/create", method = RequestMethod.GET)
    public ModelAndView vacancyCreate() {
        return new ModelAndView("vacancyCreate")
                .addObject("vacancy", new Vacancy());
    }

    @RequestMapping(path = "/vacancies/create", method = RequestMethod.POST)
    public ModelAndView vacancyCreate(@Valid @ModelAttribute Vacancy vacancy, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("vacancyCreate");
        }
        MyUserPrincipal myUserPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employer employer = employerRepository.findOne(myUserPrincipal.getUser().getId());

        vacancy.setEmployer(employer);
        vacancyRepository.save(vacancy);
        return new ModelAndView("redirect:/vacancies/create").addObject("success", true);

    }

    @RequestMapping(path = "/vacancies/{id}/edit", method = RequestMethod.GET)
    public ModelAndView vacancyEdit(@PathVariable Integer id) {
        return new ModelAndView("vacancyEdit")
                .addObject("vacancy", this.vacancyRepository.findOne(id));
    }


    @RequestMapping(path = "/vacancies/{id}/edit", method = RequestMethod.POST)
    public ModelAndView vacancyEdit(@PathVariable Integer id, @Valid @ModelAttribute Vacancy vacancy, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("vacancyEdit");
        }
        MyUserPrincipal myUserPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employer employer = employerRepository.findOne(myUserPrincipal.getUser().getId());

        vacancy.setEmployer(employer);
        vacancyRepository.save(vacancy);
        return new ModelAndView("redirect:/vacancies/" + id + "/edit").addObject("success", true);
    }

    @RequestMapping(path = "/vacancies/{id}/remove", method = RequestMethod.GET)
    public ModelAndView vacancyRemove(@PathVariable Integer id) {
        vacancyRepository.delete(id);
        return new ModelAndView("redirect:/vacancies");
    }

    @RequestMapping("/vacancies/{id}/addToFavorites")
    public String addToVacancies(@PathVariable Integer id, HttpServletRequest request) {
        MyUserPrincipal myUserPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Candidate candidate = candidateRepository.findOne(myUserPrincipal.getUser().getId());
        candidate.getFavoriteVacancies().add(this.vacancyRepository.findOne(id));
        candidateRepository.save(candidate);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }


    @RequestMapping("/vacancies/{id}/removeFromFavorites")
    public String removeFromFavorites(@PathVariable Integer id, HttpServletRequest request) {
        MyUserPrincipal myUserPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Candidate candidate = candidateRepository.findOne(myUserPrincipal.getUser().getId());
        candidate.getFavoriteVacancies().remove(this.vacancyRepository.findOne(id));
        candidateRepository.save(candidate);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @RequestMapping("/my-vacancies")
    public ModelAndView myvacancies() {
        MyUserPrincipal myUserPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employer employer = employerRepository.findOne(myUserPrincipal.getUser().getId());

        return new ModelAndView("myvacancies").addObject("myvacancies", employer.getVacancies());
    }

    @RequestMapping("/favorites")
    public ModelAndView favorites() {
        MyUserPrincipal myUserPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Candidate candidate = candidateRepository.findOne(myUserPrincipal.getUser().getId());
        return new ModelAndView("favorites").addObject("favorites",
                this.candidateRepository.findOne(candidate.getId()).getFavoriteVacancies());
    }
}
