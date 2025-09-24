/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidcom.controller;

import com.bidcom.service.ClienteService;
import com.bidcom.service.PedidoService;
import com.bidcom.service.ProductoService;
import com.bidcom.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Ramiro
 */
@Controller
public class DeleteController {

    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final PasswordEncoder passwordEncoder;


    // ✅ Inyecto todos los servicios que necesito
    public DeleteController(
            ProductoService productoService,
            UsuarioService usuarioService,
            PedidoService pedidoService,
            ClienteService clienteService,
            PasswordEncoder passwordEncoder) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping("/admin/productos/eliminar/{codigoProducto}")
    public String eliminarProducto(@PathVariable Long codigoProducto) {
        productoService.desactivar(codigoProducto);
        return "redirect:/admin/productos";
    }
    
    @PostMapping("/admin/usuarios/eliminar/{userid}")
    public String eliminarUsuario(@PathVariable Long userid) {
        usuarioService.desactivar(userid);
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/representante/pedidos/eliminar/{pedidoID}")
    public String eliminarPedido(@PathVariable Long pedidoID) {
        pedidoService.desactivar(pedidoID);
        return "redirect:/representante/pedidos";
    }
}
