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
import com.bidcom.service.PedidoService;
import com.bidcom.service.ProductoService;
import com.bidcom.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ramiro
 */
@Controller
public class CreateController {

    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final PasswordEncoder passwordEncoder;


    // ✅ Inyecto todos los servicios que necesito
    public CreateController(
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

    @GetMapping("/admin/productos/nuevo")
    public String mostrarFormularioCrearProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("formFragment", "create/form-producto");
        return "create";

    }

    @PostMapping("/admin/productos/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoService.guardar(producto);
        return "redirect:/admin/productos";
    }
    
    @GetMapping("/admin/usuarios/nuevo")
    public String mostrarFormularioCrearUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("forzarCliente", false);
        model.addAttribute("formFragment", "create/form-usuario");
        return "create";

    }

    @PostMapping("/admin/usuarios/guardar")
    public String guardarUsuario(@RequestParam("rol") String rol,
            /*
            
                ¿Puede mejorarse? ¿hacen falta todos los parametros por separado?
            
            */
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "apellido", required = false) String apellido,
            @RequestParam(name = "numeroTelefonico", required = false) String numeroTelefonico)
    {
         if (rolUsuario.CLIENTE.name().equals(rol)) {
            Cliente cliente = new Cliente();
            cliente.setRol(rolUsuario.CLIENTE);
            cliente.setEmail(email);
            //Se hashea el password con el passwordEncoder
            cliente.setPassword(passwordEncoder.encode(password)); // ¡Recuerda encriptar la contraseña!
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setNumeroTelefonico(numeroTelefonico);
            clienteService.guardar(cliente); // Asegúrate de que este método exista y funcione
        } else {
            Usuario usuario = new Usuario();
            usuario.setRol(rolUsuario.valueOf(rol));
            usuario.setEmail(email);
            usuario.setPassword(password); // ¡Recuerda encriptar la contraseña!
            usuarioService.guardar(usuario);
        }

        return "redirect:/admin/usuarios";
    }
    
    @GetMapping("/representante/clientes/nuevo")
    public String mostrarFormularioCrearClienteParaRepresentante(Model model) {
        Cliente cliente = new Cliente();
        cliente.setRol(rolUsuario.CLIENTE); // Establece el rol como "CLIENTE" por defecto
        model.addAttribute("usuario", cliente);
        model.addAttribute("forzarCliente", true);
        model.addAttribute("formFragment", "create/form-usuario");
        return "create";
    }
    
    @PostMapping("/representante/clientes/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente){
        clienteService.guardar(cliente);
        return "redirect:/representante/clientes";
    }
}
