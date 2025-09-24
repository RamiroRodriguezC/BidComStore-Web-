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

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemID;

    @ManyToOne
    @JoinColumn(name = "pedidoID", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "codigoProducto", nullable = false)
    private Producto producto;

    private Integer cantidad;

    private Double precio; // Precio en el momento del pedido
    
    // Getters y Setters
    public Long getItemID() { return itemID; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
}
