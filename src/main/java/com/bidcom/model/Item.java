package com.bidcom.model;

import jakarta.persistence.*;
import lombok.Getter; 
import lombok.Setter; 
import lombok.NoArgsConstructor; 

@Entity
@Getter 
@Setter 
@NoArgsConstructor 
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemID; 

    // Relación ManyToOne con Pedido
    @ManyToOne
    @JoinColumn(name = "pedidoID", nullable = false) 
    private Pedido pedido;

    // Relación ManyToOne con Producto
    @ManyToOne
    @JoinColumn(name = "codigoProducto", nullable = false) 
    private Producto producto;

    private Integer cantidad;

    // Precio congelado al momento de la compra 
    //si el precio del producto se altera en la base de datos no importa, item
    //ya guardo el suyo
    private Double precio; 
}