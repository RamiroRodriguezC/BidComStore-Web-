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
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido implements Desactivable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pedidoID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private Cliente cliente;

    private LocalDateTime fechaHoraCreacion;

    private String estado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<Item> items;

    @Column(name = "activo")
    private boolean activo = true;
    // Getters y Setters
    public Long getPedidoID() { return pedidoID; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public LocalDateTime getFechaHoraCreacion() { return fechaHoraCreacion; }
    public void setFechaHoraCreacion(LocalDateTime fechaHoraCreacion) { this.fechaHoraCreacion = fechaHoraCreacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    public boolean isEnabled() {
        return activo;
    }

    public void setEnabled(boolean activo) {
        this.activo = activo;
    }
    
    
}

