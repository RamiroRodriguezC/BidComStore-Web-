package com.bidcom.repositories;

import com.bidcom.model.Usuario;
import com.bidcom.model.rolUsuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>  {
    Optional<Usuario> findByEmailAndActivoTrue(String email);
    
    Optional<Usuario> findByUserid(Long userid);

    List<Usuario> findByRolAndActivoTrue(rolUsuario rol);
    
    List<Usuario> findByActivoTrue();
}

