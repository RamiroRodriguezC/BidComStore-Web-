package com.bidcom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    /**
     * Muestra la página de login.
     * @return 
     */
    @GetMapping("/login")
    public String login() {
        // La validación de si ya hay usuarios o no, la maneja el HomeController y el SetupController.
        // Retorna la vista login.html
        return "login"; 
    }
}

