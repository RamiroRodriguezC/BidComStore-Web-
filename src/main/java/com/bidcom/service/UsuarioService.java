package com.bidcom.service;

import com.bidcom.model.Producto;
import com.bidcom.model.Usuario;
import com.bidcom.model.rolUsuario;
import com.bidcom.repositories.UsuarioRepository;
import java.util.List;
import org.springframework.security.core.userdetails.User;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class UsuarioService extends BaseService<Usuario, UsuarioRepository> implements UserDetailsService  {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        super(usuarioRepository);
        this.usuarioRepository = usuarioRepository;
    }
    
    public UsuarioRepository getRepository() {
        return usuarioRepository;
    }

    public Optional<Usuario> buscarPorMail(String mail) {
        return usuarioRepository.findByEmail(mail);
    }
    
    public List<Usuario> buscarPorRol(rolUsuario rol) {
        return usuarioRepository.findByRolAndActivoTrue(rol);
    }
    
    // Editar: se hace recuperando primero el objeto
    public Usuario editar(Long id, Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setEmail(usuarioActualizado.getEmail());
        usuario.setRol(usuarioActualizado.getRol());
        usuario.setPassword(usuarioActualizado.getPassword());

        return usuarioRepository.save(usuario);
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("========= BUSCANDO POR EMAIL ========= \n " + email);
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.builder()
                .username(usuario.getEmail()) // lo que el usuario escribe en login
                .password(usuario.getPassword()) // hash de la BD
                .roles(usuario.getRol().toString()) // CLIENTE, ADMIN, REPRESENTANTE
                .build();
    }

    @Override
    public Optional<Usuario> buscarPorLlavePrimaria(Long userid) {
        return usuarioRepository.findByUserid(userid);
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findByActivoTrue();
    }
    // Crear o actualizar (Spring maneja ambos con save)

}
