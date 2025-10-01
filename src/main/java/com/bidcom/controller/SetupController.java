package com.bidcom.controller;

import com.bidcom.model.Usuario;
import com.bidcom.model.rolUsuario;
import com.bidcom.service.UsuarioService;
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
    
    // Inyección de dependencias por constructor
    public SetupController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Muestra la página de configuración inicial. Redirige si ya hay usuarios.
     */
    @GetMapping("/setup")
    public String setupPage(Model model) {
        //Si ya hay usuarios, redirigir inmediatamente a login.
        if (!usuarioService.isEmpty()) {
            return "redirect:/login"; 
        }
        
        model.addAttribute("usuario", new Usuario()); 
        return "setup"; // Retorna la vista del formulario (le pega a setup.html)
    }

    /**
     * Procesa el formulario para crear el primer usuario Administrador.
     */
    @PostMapping("/setup")
    public String createFirstAdmin(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        
        //Doble chequeo de seguridad: solo procede si NO hay usuarios
        /*  NOTA: Puse el doble chequeo para que la ruta quede lo mas segura posible
            al ser una ruta que permite crear un usuario administrador*/
        if (!usuarioService.isEmpty()) {
            return "redirect:/login"; 
        }

        // Validación básica de campos
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty() ||
            usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            
            /*a diferencia el modelAtributes, guarda el mensaje de error para 
            mostrarlo en la nueva peticion */
            redirectAttributes.addFlashAttribute("error", "El email y la contraseña son obligatorios.");
            return "redirect:/setup"; // Volver a setup pero con mensaje de error
        }

        /* NOTA: Use Early Returns por que me parecio mas corto y legible en este 
        caso con los redirects */
        
        // 1. Encrypta la contraseña con BCrypt y la guarda en el ususario
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); 
        
        // 2. Asignar rol de ADMINISTRADOR y activar
        usuario.setRol(rolUsuario.ADMINISTRADOR);
        usuario.setEnabled(true);

        // 3. Guardar el nuevo administrador en la base de datos
        usuarioService.guardar(usuario);
        
        return "redirect:/"; //va al index
    }
}


/* sin EarlyReturn

@PostMapping("/setup")
    public String createFirstAdmin(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        
        String ruta = "redirect:/";
        
        boolean setupAllowed = true;
        
        // Chequeo de seguridad: Si ya hay usuarios, el setup NO está permitido
        if (!usuarioService.isEmpty()) {
            ruta = "redirect:/login"; // La única salida es al login
            goSetup = false;
        } else {
            // Chequeo de validación: Solo si el setup está permitido
            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty() ||
                usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                
                redirectAttributes.addFlashAttribute("error", "El email y la contraseña son obligatorios.");
                ruta = "redirect:/setup"; // Volver al formulario de setup
                goSetup = false;
            }
        }
        
        // --- Lógica principal: Solo se ejecuta si goSetup es verdadero ---
        if (goSetup) {
            
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); 
            
            usuario.setRol(rolUsuario.ADMINISTRADOR);
            usuario.setEnabled(true);

            usuarioService.guardar(usuario);
            
            redirectAttributes.addFlashAttribute("success", "¡Cuenta de administrador creada! Ya puede iniciar sesión.");
        }
        
        return ruta; // Único punto de retorno
    }
*/
