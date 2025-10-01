package com.bidcom.repositories;

import com.bidcom.model.Producto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
     Optional<Producto> findByCodigoProducto(Long codigoProducto);
     
     List<Producto> findByActivoTrue();
}
