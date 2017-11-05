package com.phpdaddy.employmespring.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return "redirect:/vacancies";
        }
        return "welcome";
    }
}
