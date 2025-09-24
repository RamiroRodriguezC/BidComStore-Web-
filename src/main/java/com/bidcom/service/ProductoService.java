/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidcom.service;

import com.bidcom.model.Cliente;
import com.bidcom.model.Producto;
import com.bidcom.model.Usuario;
import com.bidcom.repositories.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ramiro
 */

@Service
public class ProductoService extends BaseService<Producto, ProductoRepository> {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        // Llama al constructor de BaseService, pasando el repositorio.
        super(productoRepository);
        this.productoRepository = productoRepository;
    }

    public ProductoRepository getRepository() {
        return productoRepository;
    }

    @Override
    public Optional<Producto> buscarPorLlavePrimaria(Long codigoProducto) {
        return productoRepository.findByCodigoProducto(codigoProducto);
    }

    public List<Producto> buscarTodos() {
        return productoRepository.findByActivoTrue();
    }
}
