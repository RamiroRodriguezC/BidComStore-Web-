package com.bidcom.config;

import com.bidcom.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UsuarioService usuarioService;

    public SecurityConfig(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 1. Configurar reglas de autorización (quién puede acceder a qué rutas)
        http.authorizeHttpRequests()
                // Permitir acceso público a recursos estáticos
                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                // Permitir acceso público a páginas públicas
                .requestMatchers("/", "/index", "/login", "/setup/**").permitAll()
                // Rutas protegidas según rol
                .requestMatchers("/clientes/**").hasRole("CLIENTE")
                .requestMatchers("/representante/**").hasRole("REPRESENTANTE_VENTAS")
                .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                // Cualquier otra petición requiere autenticación
                .anyRequest().authenticated()
            .and()

            // 2. Configurar login por formulario
            .formLogin()
                .loginPage("/login") // Página de login personalizada
                .usernameParameter("username") // Nombre del parámetro usuario en el formulario
                .passwordParameter("password") // Nombre del parámetro contraseña
                // Handler para redirigir según rol después del login exitoso
                .successHandler((request, response, authentication) -> {
                    String role = authentication.getAuthorities().iterator().next().getAuthority();
                    //en caso de que el usuario y contraseña hayan coincidido (succesHandler o redireccion inteligente)
                    //se redirige al usuario segun su rol a la pagina correspondiente
                    switch (role) {
                        case "ROLE_ADMINISTRADOR":
                            response.sendRedirect("/admin/usuarios");
                            break;
                        case "ROLE_REPRESENTANTE_VENTAS":
                            response.sendRedirect("/representante/clientes");
                            break;
                        case "ROLE_CLIENTE":
                            response.sendRedirect("/cliente/mispedidos");
                            break;
                        default:
                            response.sendRedirect("/login?error");
                            break;
                    }
                })
                .permitAll()
            .and()

            // 3. Configurar logout y permitir acceso a todos para cerrar sesión
            .logout()
                .permitAll();

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
