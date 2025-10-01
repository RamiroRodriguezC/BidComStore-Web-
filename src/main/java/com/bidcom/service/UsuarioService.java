package com.bidcom.service;
import com.bidcom.model.Usuario;
import com.bidcom.model.rolUsuario;
import com.bidcom.repositories.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
// Implementa UserDetailsService que lo va a usar SpringSecurity despues
public class UsuarioService extends BaseService<Usuario, UsuarioRepository> implements UserDetailsService {

    private final UsuarioRepository usuarioRepository; 

    public UsuarioService(UsuarioRepository usuarioRepository) {
        super(usuarioRepository); // Llama al constructor del BaseService
        this.usuarioRepository = usuarioRepository;
    }

    // --- Métodos de Búsqueda  ---
    //Busca por mail, pero solo devuelve los usuarios si estan activos
    public Optional<Usuario> buscarPorMail(String mail) {
        return usuarioRepository.findByEmailAndActivoTrue(mail);
    }
    
    //Busca por rol, pero solo devuelve los usuarios si estan activos
    public List<Usuario> buscarPorRol(rolUsuario rol) {
        return usuarioRepository.findByRolAndActivoTrue(rol);
    }
    
    // --- Lógica CRUD ---

    /**
     * Edita el email y rol de un usuario existente.
     * @param id userid
     * @param usuarioActualizado
     * @return 
     */
    public Usuario editar(Long id, Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepository.findById(id) 
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setEmail(usuarioActualizado.getEmail());
        usuario.setRol(usuarioActualizado.getRol());
        
        // No manejamos la contraseña aquí por seguridad.

        return usuarioRepository.save(usuario);
    }
    
    // --- Métodos de UserDetailsService (Spring Security) ---

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = buscarPorMail(email) 
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado o inactivo"));

        return User.builder()
                .username(usuario.getEmail()) 
                .password(usuario.getPassword()) 
                .roles(usuario.getRol().toString()) 
                .build();
    }

    // --- Implementación de BaseService (Método abstracto) ---
    @Override
    public Optional<Usuario> buscarPorLlavePrimaria(Long userid) {
        return usuarioRepository.findByUserid(userid);
    }
    
    // --- Lógica del Wizzard / Pantalla de registro del primer user ---

    /**
     * Verifica si la tabla de usuarios está vacía. Usado por el SetupController.
     */
    public boolean isEmpty() {
        // Limpiamos el debugging y usamos el método de conteo.
        return usuarioRepository.count() == 0;
    }
    
    // El método guardar(T entidad) y buscarTodos() se heredan del BaseService
}