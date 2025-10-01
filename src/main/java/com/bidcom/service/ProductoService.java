package com.bidcom.service;

import com.bidcom.model.Producto;
import com.bidcom.repositories.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductoService extends BaseService<Producto, ProductoRepository> {
    
    // Mantenemos el atributo local para facilitar la lectura y evitar el casting
    private final ProductoRepository productoRepository; 

    public ProductoService(ProductoRepository productoRepository) {
        // Inicializamos el repositorio en el padre (BaseService)
        super(productoRepository);
        // Asignamos el repositorio al atributo local
        this.productoRepository = productoRepository; 
    }

    // Método para acceder al repositorio si fuera necesario, aunque ya está disponible localmente
    public ProductoRepository getRepository() {
        return productoRepository;
    }

    @Override
    public Optional<Producto> buscarPorLlavePrimaria(Long codigoProducto) {
        // Implementación directa usando el atributo local, sin casting
        return productoRepository.findByCodigoProducto(codigoProducto);
    }
}