/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidcom.controller;

import com.bidcom.model.Cliente;
import com.bidcom.model.Producto;
import com.bidcom.model.Usuario;
import com.bidcom.model.rolUsuario;
import com.bidcom.service.ClienteService;
import com.bidcom.service.ProductoService;
import com.bidcom.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ramiro
 */
@Controller
public class EditController {

    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final ClienteService clienteService;
    
    public EditController(ProductoService productoService, UsuarioService usuarioService, ClienteService clienteService, PasswordEncoder passwordEncoder) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.clienteService = clienteService;
    }

    // Mostrar formulario de edición
    @GetMapping("admin/productos/edit/{codigoProducto}")
    public String editarProducto(@PathVariable Long codigoProducto, Model model) {
        Producto producto = productoService.buscarPorLlavePrimaria(codigoProducto)
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

    
    @GetMapping({"/admin/usuarios/edit/{userid}", "/representante/cliente/edit/{userid}"})
    public String editarUsuario(@PathVariable Long userid, Model model, HttpServletRequest request) {
        Usuario usuario = usuarioService.buscarPorLlavePrimaria(userid)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + userid));

        // Detectamos si la URL contiene "/representante/" para definir calledByRepresentante
        boolean calledByRepresentante = request.getRequestURI().contains("/representante/");

        model.addAttribute("usuario", usuario);
        model.addAttribute("calledByRepresentante", calledByRepresentante);
        model.addAttribute("formFragment", "edit/edit-usuario");

        return "edit";
    }

    @PostMapping("/admin/usuarios/actualizar")
    public String actualizarUsuarioDesdeAdmin(@ModelAttribute("usuario") Usuario usuarioForm) {
        return actualizarUsuario(usuarioForm, false);
    }

    @PostMapping("/representante/clientes/actualizar")
    public String actualizarUsuarioDesdeRepresentante(@ModelAttribute("usuario") Cliente clienteForm) {
        return actualizarUsuario(clienteForm, true);
    }


@Transactional
private String actualizarUsuario(Usuario usuarioForm, boolean calledByRepresentante) {
    Usuario usuarioOriginal = usuarioService.buscarPorLlavePrimaria(usuarioForm.getUserid())
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuarioForm.getUserid()));
    
    //actualiza el campo mail
    usuarioOriginal.setEmail(usuarioForm.getEmail());

    //actualiza el campo password (si el campo se dejo vacio se omite)
    if (usuarioForm.getPassword() != null && !usuarioForm.getPassword().isEmpty()) {
        //le pasa la contraseña a usuarioOriginal una vez hasheada
        usuarioOriginal.setPassword(passwordEncoder.encode(usuarioForm.getPassword()));
    }

    // Solo si es Cliente, actualizamos campos específicos
        if (usuarioOriginal instanceof Cliente && usuarioForm instanceof Cliente) {
        Cliente clienteOriginal = (Cliente) usuarioOriginal;
        Cliente clienteForm = (Cliente) usuarioForm;

        clienteOriginal.setNombre(clienteForm.getNombre());
        clienteOriginal.setApellido(clienteForm.getApellido());
        clienteOriginal.setNumeroTelefonico(clienteForm.getNumeroTelefonico());
    }

    // Guardamos siempre a través de usuarioService
    usuarioService.guardar(usuarioOriginal);

    return calledByRepresentante ? "redirect:/representante/clientes" : "redirect:/admin/usuarios";
}



}
