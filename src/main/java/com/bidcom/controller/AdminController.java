/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidcom.controller;

import com.bidcom.model.Usuario;
import com.bidcom.repositories.ProductoRepository;
import com.bidcom.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Ramiro
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

@GetMapping("/usuarios")
public String verUsuarios(Model model) {
    model.addAttribute("usuarios", usuarioRepository.findAll());
    return "admin/layout"; // Thymeleaf reemplazará el fragmento directamente
}



    @GetMapping("/productos")
    public String verProductos(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "admin/productos";
    }
    
    @GetMapping("/usuarios-test")
    public String testUsuarios() {
        return "admin/usuarios";
    }
}
