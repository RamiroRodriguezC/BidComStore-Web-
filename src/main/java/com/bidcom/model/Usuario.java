package com.bidcom.model;

/**
 *
 * @author Ramiro
 */
import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuarios")
public class Usuario implements UserDetails,Desactivable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private rolUsuario rol;

    @Column(name = "activo")
    private boolean activo = true;

    // Getters y Setters
    public Long getUserid() {
        return userid;
    }

    public Long getUserID() {
        return getUserid();
    }

    ;
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public rolUsuario getRol() {
        return this.rol;
    }

    public void setRol(rolUsuario rol) {
        this.rol = rol;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }

    @Override
    public void setEnabled(boolean activo) {
        this.activo = activo;
    }
    
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name())); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        /* 
           Cada rol del usuario se transforma en un "GrantedAuthority"
           Spring Security usa esta colección para decidir si el usuario tiene permiso
           para acceder a un endpoint protegido (por ejemplo, /admin/** requiere ROLE_ADMINISTRADOR)
           Se agrega "ROLE_" al nombre del rol porque hasRole("X") internamente busca "ROLE_X" 
         */
    }

    @Override
    public String getUsername() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    /**
     * Devuelve el cliente asociado si el usuario es un Cliente. Retorna null si
     * no es un Cliente (por ejemplo Administrador o Representante).
     */
    public Cliente getCliente() {
        if (this instanceof Cliente) { //instanceof devuelve True si This (el usuario) es del tipo cliente
            return (Cliente) this; //si lo es, lo devuelve casteado a cliente,
        }
        return null; //si no lo devuelve nulo
    }
}
