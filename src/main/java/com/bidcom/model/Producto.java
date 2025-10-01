package com.bidcom.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter // Genera todos los getters
@Setter // Genera todos los setters
@NoArgsConstructor // Constructor sin argumentos para JPA
@Table(name = "productos")
public class Producto implements Desactivable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoProducto; 

    private String nombre;
    private String modelo;
    private String descripcion;
    private Double precio; 
    
    @Column(name = "activo")
    private boolean activo = true; 
    
    // --- Implementación de la Interfaz Desactivable ---

    /**
     * Indica si el producto está disponible (activo = true).
     */
    @Override
    public boolean isEnabled() {
        return activo;
    }

    /**
     * Establece el estado de disponibilidad del producto.
     */
    @Override
    public void setEnabled(boolean activo) {
        this.activo = activo;
    }
}
