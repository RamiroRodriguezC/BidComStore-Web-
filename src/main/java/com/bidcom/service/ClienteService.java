/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidcom.service;

import com.bidcom.model.Cliente;
import com.bidcom.repositories.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ramiro
 */
@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional // Asegura que la operación sea atómica, que se haga toda junta, si por algun motivo
                   //algo no se hace, entonces se baja toda la operacion.
                   //o todo se realiza con exito o no se realiza nada.
    public void guardar(Cliente cliente) {
        clienteRepository.save(cliente);
    }
}