/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidcom.controller;

import com.bidcom.model.Producto;
import com.bidcom.model.Usuario;
import com.bidcom.service.ProductoService;
import com.bidcom.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Ramiro
 */
@Controller
public class EditController {

    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    public EditController(ProductoService productoService, UsuarioService usuarioService) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    // Mostrar formulario de edición
    @GetMapping("admin/productos/edit/{codigoProducto}")
    public String editarProducto(@PathVariable Long codigoProducto, Model model) {
        Producto producto = productoService.buscarPorCodigo(codigoProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + codigoProducto));
        model.addAttribute("producto", producto);
        model.addAttribute("formFragment", "edit/edit-producto");
        return "edit";
    }

    // Guardar cambios
    @PostMapping("/admin/productos/actualizar")
    public String actualizarProducto(@ModelAttribute Producto producto) {
        productoService.guardar(producto); // save actúa como update si el ID existe
        return "redirect:/admin/productos";
    }
    
        @GetMapping("admin/usuarios/edit/{userdID}")
    public String editarUsuario(@PathVariable Long userid, Model model) {
        Usuario usuario = usuarioService.buscarPorID(userid)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + userid));
        model.addAttribute("usuario", usuario);
        model.addAttribute("formFragment", "edit/edit-usuario");
        return "edit";
    }

    // Guardar cambios
    @PostMapping("/admin/usuarios/actualizar")
    public String actualizarUsuario(@ModelAttribute Usuario usuario) {
        System.out.println("ID recibido: " + usuario.getUserID());
        usuarioService.guardar(usuario); // save actúa como update si el ID existe
        return "redirect:/admin/usuarios";
    }
}
