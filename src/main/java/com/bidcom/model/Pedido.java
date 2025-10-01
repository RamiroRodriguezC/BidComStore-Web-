package com.bidcom.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter; 
import lombok.Setter; 
import lombok.NoArgsConstructor; 

@Entity
@Getter 
@Setter 
@NoArgsConstructor 
@Table(name = "pedidos")
public class Pedido implements Desactivable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pedidoID; 

    // Relación ManyToOne con Cliente
    @ManyToOne 
    @JoinColumn(name = "userID", nullable = false) 
    private Cliente cliente;

    private LocalDateTime fechaHoraCreacion; 

    private String estado; 

    // Si se borra el pedido, se eliminan tambien los items (CascadeType.ALL)
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

    @Column(name = "activo")
    private boolean activo = true;

    // --- Implementación de Desactivable  ---
    
    @Override
    public boolean isEnabled() { return activo; }
    
    @Override
    public void setEnabled(boolean activo) { this.activo = activo; }
}