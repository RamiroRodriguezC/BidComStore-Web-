/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidcom.controller;

import com.bidcom.repositories.PedidoRepository;
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
@RequestMapping("/representante")
public class RepresentanteController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/clientes")
    public String verUsuarios(Model model) {
        model.addAttribute("clientes", usuarioRepository.findAll());
        model.addAttribute("fragment", "representante/clientes");
        return "layout"; // Thymeleaf reemplazará el fragmento directamente
    }
    
    @GetMapping("/pedidos")
    public String verPedidos(Model model) {
        model.addAttribute("pedidos", pedidoRepository.findAll());
        model.addAttribute("fragment", "representante/pedidos");
        return "layout"; // Thymeleaf reemplazará el fragmento directamente
    }
}

