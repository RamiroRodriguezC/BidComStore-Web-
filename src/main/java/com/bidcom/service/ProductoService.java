/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidcom.service;

import com.bidcom.model.Producto;
import com.bidcom.model.Usuario;
import com.bidcom.repositories.ProductoRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ramiro
 */

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    
    public ProductoRepository getRepository() {
        return productoRepository;
    }
    
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }
    
    public Optional<Producto> buscarPorCodigo(Long codigoProducto) {
        return productoRepository.findByCodigoProducto(codigoProducto);
    }
}
