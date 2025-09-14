/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidcom.model;

/**
 *
 * @author Ramiro
 */


import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {
    private String nombre;
    private String apellido;
    private String numeroTelefonico;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    @OneToOne
    @JoinColumn(name = "userid") // FK hacia Usuario
    // Getters y Setters

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getNumeroTelefonico() { return numeroTelefonico; }
    public void setNumeroTelefonico(String numeroTelefonico) { this.numeroTelefonico = numeroTelefonico; }

    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }
    
    public Usuario getUsuario() { return this; }
}
