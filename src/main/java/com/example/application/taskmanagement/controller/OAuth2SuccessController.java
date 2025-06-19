package com.example.application.taskmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2SuccessController {
    @GetMapping("/oauth2-success")
    public String redirectAfterLogin() {
        System.out.println("OAuth2 login successful, redirecting to home page.");
        return "redirect:/home"; // or any view
    }
}