package com.bidcom.controller;

import com.bidcom.model.Usuario;
import com.bidcom.model.rolUsuario;
import com.bidcom.repositories.UsuarioRepository;
import com.bidcom.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public LoginController() {
        this.usuarioRepository = null;
        this.usuarioService = null;
    }
    
    @GetMapping("/login")
    public String login() {
        
        return "login"; // busca login.html en /templates
    }
    
    
}


