package com.bidcom.controller;

import com.bidcom.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    private final UsuarioService usuarioService;

    public HomeController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Maneja la ruta de inicio. 
     * Determina si la aplicación ya fue inicializada.
     */
    @GetMapping({"/", "/index"})
    public String index(Model model) {
        // Usa la bandera para indicar si el wizzard de setup es necesario
        boolean hayUsuarios = !usuarioService.isEmpty(); 
        
        /*este atributo que le pasamos a la vista sirve para que cambie el 
        nombre del boton, despues se redirecciona en setup */
        model.addAttribute("hayUsuarios", hayUsuarios);
        
        // Retorna la vista index.html
        return "index"; 
    }
}