package com.bidcom.controller;

import com.bidcom.model.Usuario;
import com.bidcom.model.rolUsuario;
import com.bidcom.repositories.UsuarioRepository;
import com.bidcom.service.UsuarioService; // Aunque no se use directamente aquí, es buena práctica
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SetupController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    
    // Inyección de dependencias necesaria para acceder a la BD y codificar la contraseña
    public SetupController(PasswordEncoder passwordEncoder, UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Muestra la página de configuración inicial. Redirige si ya hay usuarios.
     */
    @GetMapping("/setup")
    public String setupPage(Model model) {
        // Solo permitir el acceso si la base de datos está vacía
        if (!usuarioService.isEmpty()) {
            System.out.println("\n\n\n\n\n piribiri"+usuarioService.isEmpty() + "\n\n\n\n\n");
            return "redirect:/login";
        }
        
        // Objeto para enlazar con el formulario Thymeleaf (setup.html)
        model.addAttribute("usuario", new Usuario()); 
        return "setup"; // Busca setup.html en /templates
    }

    /**
     * Procesa el formulario para crear el primer usuario Administrador.
     */
    @PostMapping("/setup")
    public String createFirstAdmin(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        // Doble chequeo de seguridad: solo procede si NO hay usuarios

        // Validación básica de campos
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty() ||
            usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El email y la contraseña son obligatorios.");
            return "redirect:/setup";
        }

        // 1. Configurar el usuario como ADMINISTRADOR con los datos proporcionados
        Usuario newAdmin = new Usuario();
        newAdmin.setEmail(usuario.getEmail());
        // 2. ¡IMPORTANTE! Codificar la contraseña con BCrypt
        newAdmin.setPassword(passwordEncoder.encode(usuario.getPassword())); 
        newAdmin.setRol(rolUsuario.ADMINISTRADOR);
        newAdmin.setEnabled(true);

        // 3. Guardar el nuevo administrador en la base de datos
        usuarioService.guardar(newAdmin);
        
        // 4. Redirigir al login con mensaje de éxito
        redirectAttributes.addFlashAttribute("success", "¡Cuenta de administrador creada! Inicie sesión con su nuevo usuario.");
        return "redirect:/login";
    }
}