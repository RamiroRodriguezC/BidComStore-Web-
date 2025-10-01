package com.bidcom.controller;

import com.bidcom.service.ProductoService;
import com.bidcom.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsuarioService usuarioService;
    private final ProductoService productoService;

    // Inyección de dependencias por constructor (mejor práctica)
    public AdminController(UsuarioService usuarioService, ProductoService productoService) {
        this.usuarioService = usuarioService;
        this.productoService = productoService;
    }

    /**
     * Muestra la tabla de usuarios activos en el panel de administración.
     * La lista solo incluye usuarios con activo=true, según UsuarioService.
     */
    @GetMapping("/usuarios")
    public String verUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.buscarTodos()); //todos donde activo = true
        model.addAttribute("fragment", "admin/usuarios");
        model.addAttribute("crearUrl", "/admin/usuarios/nuevo");
        return "layout"; 
    }

    /**
     * Muestra la tabla de productos activos en el panel de administración.
     * La lista solo incluye productos con activo=true, según ProductoService.
     */
    @GetMapping("/productos")
    public String verProductos(Model model) {
        model.addAttribute("productos", productoService.buscarTodos()); //todos donde activo = true
        model.addAttribute("fragment", "admin/productos");
        model.addAttribute("crearUrl", "/admin/productos/nuevo");
        return "layout";
    }
}
