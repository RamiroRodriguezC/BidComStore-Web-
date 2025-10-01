package com.bidcom.service;
import com.bidcom.model.Desactivable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 *
 * @author Ramiro
 * @param <T> Objeto del modelo (entidad JPA) Desactivable
 * @param <R> Repositorio (correspondiente al modelo)
 */

//NOTA: T = Type (tipo), es convencion nombrarlo asi, en este caso es la entidad JPA
public abstract class BaseService<T extends Desactivable, R extends JpaRepository<T, Long>> {

    private final R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }

    public abstract Optional<T> buscarPorLlavePrimaria(Long pk);
    
    @Transactional
    public void desactivar(Long pk) {
        Optional<T> entidadOptional = buscarPorLlavePrimaria(pk);
        if (entidadOptional.isPresent()) {
            T entidad = entidadOptional.get();
            entidad.setEnabled(false);
            repository.save(entidad);
        }
    }
    
    // Métodos comunes de CRUD que usan el repositorio
    public List<T> buscarTodos() {
        return repository.findAll();
    }
    
    public T guardar(T entidad) {
        return repository.save(entidad);
    }
 
    //Este por el momento no esta usado, pero podria llegar a utilizarse
    /* public List<T> buscarTodos() {
        return repository.findAll();
    } */
}