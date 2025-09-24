package com.bidcom.config;

import com.bidcom.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
        http
                .authorizeHttpRequests()
                //Permitir el acceso publico a los recursos estaticos
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // el resto pide login

                //Definir los endpoints publicos
                .requestMatchers("/", "/index", "/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/clientes/**").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.POST, "/representante/**").hasRole("REPRESENTANTE_VENTAS")
                .requestMatchers(HttpMethod.POST,"/admin/**").hasRole("ADMINISTRADOR")
                // el /** significa que pueden entrar a todas las subrutas de x/**
                // osea, si tengo admin/** puedo entrar a admin/a y admin/b por igual
                .anyRequest().authenticated()
                .and()
                //Definir la pagina de login / inicio de sesion
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username") // el formulario realmente usa la columna "email"
                .passwordParameter("password")
                
                .successHandler((request, response, authentication) -> {
                    String role = authentication.getAuthorities().iterator().next().getAuthority();
                    //en caso de que el usuario y contraseña hayan coincidido (succesHandler)
                    //se redirige al usuario segun su rol a la pagina correspondiente
                    
                    if (role.equals("ROLE_ADMINISTRADOR")) { //Hibernate le agrega el prefijo ROLE_
                        response.sendRedirect("/admin/usuarios");
                    } else if (role.equals("ROLE_REPRESENTANTE_VENTAS")) { //Hibernate le agrega el prefijo ROLE_
                        System.out.println("ROL = " + role);
                        response.sendRedirect("/representante/clientes");
                    } else {
                        System.out.println(role.toString());
                        response.sendRedirect("/cliente/mispedidos");
                    }
                })
                .permitAll()
                .and()
                //Definir la pagina de logout / salida
                .logout(logout -> logout.permitAll());

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
