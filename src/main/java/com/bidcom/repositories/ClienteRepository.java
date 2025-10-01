package com.bidcom.repositories;

import com.bidcom.model.Cliente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByActivoTrue();
    
    Optional<Cliente> findByUserid(Long userid);
}

