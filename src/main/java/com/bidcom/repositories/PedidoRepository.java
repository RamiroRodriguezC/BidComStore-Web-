package com.bidcom.repositories;

import com.bidcom.model.Cliente;
import com.bidcom.model.Pedido;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Busca los pedidos de un cliente. 
    // Usamos 'Cliente' en lugar de 'Usuario' para reflejar el tipo de la relación.
    List<Pedido> findByCliente(Cliente cliente); // <--- CAMBIO AQUÍ
    
    // Búsqueda por clave primaria
    Optional<Pedido> findByPedidoID(Long pedidoID);
    
    // Búsqueda de pedidos activos
    List<Pedido> findByActivoTrue();
}

