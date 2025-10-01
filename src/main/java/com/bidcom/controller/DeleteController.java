package com.bidcom.controller;

import com.bidcom.service.PedidoService;
import com.bidcom.service.ProductoService;
import com.bidcom.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DeleteController {

    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final PedidoService pedidoService;


    // Inyección de dependencias por constructor
    public DeleteController(
            ProductoService productoService,
            UsuarioService usuarioService,
            PedidoService pedidoService) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.pedidoService = pedidoService;
    }
    
    // --- SOFT DELETE PRODUCTO (Admin) ---

    @PostMapping("/admin/productos/eliminar/{codigoProducto}")
    public String eliminarProducto(@PathVariable Long codigoProducto) {
        productoService.desactivar(codigoProducto);
        return "redirect:/admin/productos";
    }
    
    // --- SOFT DELETE USUARIO (Admin) ---

    @PostMapping("/admin/usuarios/eliminar/{userid}")
    public String eliminarUsuario(@PathVariable Long userid) {
        // Llama al método de soft delete
        usuarioService.desactivar(userid); 
        return "redirect:/admin/usuarios";
    }

    // --- SOFT DELETE PEDIDO (Representante) ---

    @PostMapping("/representante/pedidos/eliminar/{pedidoID}")
    public String eliminarPedido(@PathVariable Long pedidoID) {
        pedidoService.desactivar(pedidoID);
        return "redirect:/representante/pedidos";
    }
    
    // --- SOFT DELETE CLIENTE (Representante) ---
    
    @PostMapping("/representante/clientes/eliminar/{userid}")
    public String eliminarCliente(@PathVariable Long userid) {
        // Cliente hereda de Usuario, por lo que el Service de Usuario lo maneja
        usuarioService.desactivar(userid); 
        return "redirect:/representante/clientes";
    }
}