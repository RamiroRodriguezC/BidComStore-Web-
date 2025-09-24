/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidcom.controller;

import com.bidcom.model.Usuario;
import com.bidcom.repositories.ProductoRepository;
import com.bidcom.repositories.UsuarioRepository;
import com.bidcom.service.ProductoService;
import com.bidcom.service.UsuarioService;
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
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

@GetMapping("/usuarios")
public String verUsuarios(Model model) {
    model.addAttribute("usuarios", usuarioService.buscarTodos());
    model.addAttribute("fragment", "admin/usuarios");
    model.addAttribute("crearUrl", "/admin/usuarios/nuevo");
    return "layout"; // Thymeleaf reemplazará el fragmento directamente
}

@GetMapping("/productos")
public String verProductos(Model model) {
    model.addAttribute("productos", productoService.buscarTodos());
    model.addAttribute("fragment", "admin/productos");
    model.addAttribute("crearUrl", "/admin/productos/nuevo");
    return "layout";
}
    

}
