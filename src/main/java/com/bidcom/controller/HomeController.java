package com.bidcom.controller;

import com.bidcom.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Ramiro
 */


@Controller
public class HomeController {

    private final UsuarioService usuarioService;

    public HomeController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        boolean hayUsuarios = !usuarioService.isEmpty();
        System.out.println(usuarioService.buscarTodos());
        model.addAttribute("hayUsuarios", hayUsuarios);
        return "index"; // Thymeleaf
    }
}
