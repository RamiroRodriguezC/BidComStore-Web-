package com.bidcom.controller;

import com.bidcom.model.rolUsuario;
import com.bidcom.service.PedidoService;
import com.bidcom.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/representante")
public class RepresentanteController {

    private final UsuarioService usuarioService;
    private final PedidoService pedidoService;

    public RepresentanteController(UsuarioService usuarioService, PedidoService pedidoService) {
        this.usuarioService = usuarioService;
        this.pedidoService = pedidoService;
    }

    /**
     * Muestra la lista de todos los clientes activos.
     */
    @GetMapping("/clientes")
    public String verClientes(Model model) {
        // Usa el Service para buscar solo los usuarios activos (con rol CLIENTE)
        model.addAttribute("clientes", usuarioService.buscarPorRol(rolUsuario.CLIENTE));
        model.addAttribute("fragment", "representante/clientes");
        model.addAttribute("crearUrl", "/representante/clientes/nuevo");
        return "layout"; 
    }
    
    /**
     * Muestra la lista de todos los pedidos activos en el sistema.
     */
    @GetMapping("/pedidos")
    public String verPedidos(Model model) {
        // Usa el Service para obtener SOLO los pedidos activos
        model.addAttribute("pedidos", pedidoService.buscarTodos());
        model.addAttribute("fragment", "representante/pedidos");
        model.addAttribute("crearUrl", "/representante/pedidos/nuevo");
        return "layout";
    }
}