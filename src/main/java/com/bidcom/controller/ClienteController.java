
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidcom.controller;

/**
 *
 * @author Ramiro
 */

import com.bidcom.model.Cliente;
import com.bidcom.model.Pedido;
import com.bidcom.model.Usuario;
import com.bidcom.repositories.ClienteRepository;
import com.bidcom.repositories.PedidoRepository;
import com.bidcom.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class ClienteController {

    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;

    public ClienteController(UsuarioRepository usuarioRepository, PedidoRepository pedidoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping("/clientes")
    public String verPedidosCliente(Model model) {
        // obtiene quien esta logueado (devolveria el mail)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        String email = auth.getName();
        // lo busca en la base de datos
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // traer pedidos solo de este cliente
        List<Pedido> pedidos = pedidoRepository.findByCliente(usuario);
        
        //aca basicente le pasa las variables pedidos y clientes al html
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("cliente", usuario.getCliente());
        return "clientes"; // clientes.html en templates
    }
}

