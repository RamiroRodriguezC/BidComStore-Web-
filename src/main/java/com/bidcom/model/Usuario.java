package com.bidcom.model;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Getter;
import lombok.Setter; 
import lombok.NoArgsConstructor;

@Entity
@Getter // Genera automáticamente todos los Getters
@Setter // Genera automáticamente todos los Setters
@NoArgsConstructor // Genera un constructor sin argumentos
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuarios")
public class Usuario implements UserDetails, Desactivable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid; 

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private rolUsuario rol;

    //siempre se va a inicializar en true
    @Column(name = "activo")
    private boolean activo = true;

    // --- Métodos de la Interfaz UserDetails ---
    @Override
    public String getUsername() {
        return this.email;
    }
    
    /**
     * Asigna las autoridades (roles) que Spring Security usará para la autorización.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Asegura el prefijo "ROLE_" para la compatibilidad con hasRole()
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name())); 
    }

    // --- Implementación de la Interfaz Desactivable ---
    /**
     * Utiliza el campo 'activo' de la entidad para determinar si el usuario puede loguearse.
     */
    @Override
    public boolean isEnabled() {
        return activo;
    }
    
    @Override
    public void setEnabled(boolean activo) {
        this.activo = activo;
    }
    
    // --- Métodos Adicionales (Limpiamos el método redundante getUserID) ---

    /**
     * Devuelve el cliente asociado si el usuario es un Cliente. 
     * Retorna null si no lo es.
     */
    public Cliente getCliente() {
        if (this instanceof Cliente) {
            return (Cliente) this;
        }
        return null;
    }
}