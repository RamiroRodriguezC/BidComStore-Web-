/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bidcom.repositories;

import com.bidcom.model.Pedido;
import com.bidcom.model.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    //Busca los pedidos de un cliente, lo filtra por la id del cliente
    List<Pedido> findByCliente(Usuario cliente);
    
    //
    Optional<Pedido> findByPedidoID(Long pedidoID);
    
    List<Pedido> findByActivoTrue();
}

