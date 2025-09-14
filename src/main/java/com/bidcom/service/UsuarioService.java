package com.bidcom.service;

import com.bidcom.model.Usuario;
import com.bidcom.repositories.UsuarioRepository;
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

public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    public UsuarioRepository getRepository() {
        return usuarioRepository;
    }

    /*public Optional<Usuario> buscarPorUsername(String email) {
        return repository.findByEmail(email);
    }

    public boolean validarLogin(String email, String password) {
        return repository.findByEmail(email)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }*/

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("========= BUSCANDO POR EMAIL ========= \n " + email );
         Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.builder()
                .username(usuario.getEmail())     // lo que el usuario escribe en login
                .password(usuario.getPassword())             // hash de la BD
                .roles(usuario.getRol().toString())      // CLIENTE, ADMIN, REPRESENTANTE
                .build();
    }

}
