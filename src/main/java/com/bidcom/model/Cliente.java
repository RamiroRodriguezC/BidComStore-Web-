package com.bidcom.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter // Genera automáticamente todos los Getters
@Setter // Genera automáticamente todos los Setters
@NoArgsConstructor // Genera un constructor sin argumentos
@Table(name = "clientes")
public class Cliente extends Usuario {
    
    // Atributos específicos de Cliente
    private String nombre;
    private String apellido;
    private String numeroTelefonico;

    // Relación OneToMany con Pedido
    @OneToMany(mappedBy = "cliente", orphanRemoval = true) 
    private List<Pedido> pedidos;
}