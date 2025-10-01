package com.bidcom.controller;

import com.bidcom.model.Cliente;
import com.bidcom.model.Producto;
import com.bidcom.model.Usuario;
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

@Controller
public class EditController {

    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    
    public EditController(ProductoService productoService, UsuarioService usuarioService,PasswordEncoder passwordEncoder) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    // --- CRUD PRODUCTO ---
    
    @GetMapping("admin/productos/edit/{codigoProducto}")
    public String editarProducto(@PathVariable Long codigoProducto, Model model) {
        Producto producto = productoService.buscarPorLlavePrimaria(codigoProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + codigoProducto));
        model.addAttribute("producto", producto);
        model.addAttribute("formFragment", "edit/edit-producto");
        return "edit";
    }

    @PostMapping("/admin/productos/actualizar")
    public String actualizarProducto(@ModelAttribute Producto producto) {
        productoService.guardar(producto); // save afunciona como update si el ID existe
        return "redirect:/admin/productos";
    }
    
    // --- CRUD USUARIO/CLIENTE (Mostrar Formulario) ---

    @GetMapping({"/admin/usuarios/edit/{userid}", "/representante/clientes/edit/{userid}"})
    public String editarUsuario(@PathVariable Long userid, Model model, HttpServletRequest request) {
        Usuario usuario = usuarioService.buscarPorLlavePrimaria(userid)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + userid));

        // Detectamos si la URL contiene "/representante/" para definir calledByRepresentante
        //si calledByRepresentante es true, el html va a fijar cliente en el tipo de usuario
        //tambien se usa para manejar el actualizarUsuario que es para admin y repre
        boolean calledByRepresentante = request.getRequestURI().contains("/representante/");
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("calledByRepresentante", calledByRepresentante);
        model.addAttribute("formFragment", "edit/edit-usuario");

        return "edit";
    }
    
    // --- CRUD USUARIO/CLIENTE (Guardar Desde Admin) ---

    @PostMapping("/admin/usuarios/actualizar")
    public String actualizarUsuarioDesdeAdmin(@ModelAttribute("usuario") Usuario usuarioForm) { 
        // Lógica de cast y campos de Cliente se maneja dentro de actualizarUsuario
        return actualizarUsuario(usuarioForm, false); 
    }

    // --- CRUD USUARIO/CLIENTE (Guardar Desde Representante) ---
    
    @PostMapping("/representante/clientes/actualizar")
    // Mantenemos Cliente aca, pq el Representante SOLO debe poder actualizar Clientes
    public String actualizarUsuarioDesdeRepresentante(@ModelAttribute("usuario") Cliente clienteForm) { 
        return actualizarUsuario(clienteForm, true);
    }


    // --- LÓGICA CENTRAL DE ACTUALIZACIÓN ---

    @Transactional
    private String actualizarUsuario(Usuario usuarioForm, boolean calledByRepresentante) {
        Usuario usuarioOriginal = usuarioService.buscarPorLlavePrimaria(usuarioForm.getUserid())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuarioForm.getUserid()));
        
        // Actualiza el Email (campo común)
        usuarioOriginal.setEmail(usuarioForm.getEmail());

        // Actualiza el password solo si se proporcionó uno nuevo (campo comun)
        if (usuarioForm.getPassword() != null && !usuarioForm.getPassword().isEmpty()){ 
            //ademas hashea la entrada del campo con BCrypt
            usuarioOriginal.setPassword(passwordEncoder.encode(usuarioForm.getPassword()));
        }

        // Si es cliente, actualizamos sus campos especificos /nombre, apellido, telefono)
        if (usuarioOriginal instanceof Cliente && usuarioForm instanceof Cliente) {
            Cliente clienteOriginal = (Cliente) usuarioOriginal;
            Cliente clienteForm = (Cliente) usuarioForm;

            clienteOriginal.setNombre(clienteForm.getNombre());
            clienteOriginal.setApellido(clienteForm.getApellido());
            clienteOriginal.setNumeroTelefonico(clienteForm.getNumeroTelefonico());
        }
        
        // Si se selecciono algo en el campo rol (que solo lo tiene el admin)
        // Se actualiza el rol
        if (!calledByRepresentante && usuarioForm.getRol() != null) {
            usuarioOriginal.setRol(usuarioForm.getRol());
        }

        // Guardamos
        usuarioService.guardar(usuarioOriginal);

        return calledByRepresentante ? "redirect:/representante/clientes" : "redirect:/admin/usuarios";
    }
}