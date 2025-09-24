/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidcom.controller;

import com.bidcom.model.rolUsuario;
import com.bidcom.repositories.PedidoRepository;
import com.bidcom.repositories.ProductoRepository;
import com.bidcom.repositories.UsuarioRepository;
import com.bidcom.service.PedidoService;
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
@RequestMapping("/representante")
public class RepresentanteController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/clientes")
    public String verUsuarios(Model model) {
        model.addAttribute("clientes", usuarioService.buscarPorRol(rolUsuario.CLIENTE));
        model.addAttribute("fragment", "representante/clientes");
        model.addAttribute("crearUrl", "/representante/clientes/nuevo");
        return "layout"; // Thymeleaf reemplazará el fragmento directamente
    }
    
    @GetMapping("/pedidos")
    public String verPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.buscarTodos());
        model.addAttribute("fragment", "representante/pedidos");
        model.addAttribute("crearUrl", "/representante/pedidos/nuevo");
        return "layout"; // Thymeleaf reemplazará el fragmento directamente
    }
}

