/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidcom.service;

import com.bidcom.model.Cliente;
import com.bidcom.model.Pedido;
import com.bidcom.model.Usuario;
import com.bidcom.repositories.PedidoRepository;
import com.bidcom.repositories.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ramiro
 */

@Service
public class PedidoService extends BaseService<Pedido, PedidoRepository> {
    private final PedidoRepository pedidoRepository;
    
    protected PedidoService(PedidoRepository pedidoRepository) {
        super(pedidoRepository);
        this.pedidoRepository = pedidoRepository;
    }
    
    public PedidoRepository getRepository() {
        return pedidoRepository;
    }

    @Override
    public Optional<Pedido> buscarPorLlavePrimaria(Long pedidoID) {
        return pedidoRepository.findByPedidoID(pedidoID);
    }
    
    public List<Pedido> buscarTodos() {
        return pedidoRepository.findByActivoTrue();
    }
    
    public List<Pedido> buscarPedidosDeCliente(Usuario cliente){
        return pedidoRepository.findByCliente(cliente);
    }
}

