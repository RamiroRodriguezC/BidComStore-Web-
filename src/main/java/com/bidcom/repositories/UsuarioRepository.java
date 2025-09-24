/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bidcom.repositories;

/**
 *
 * @author Ramiro
 */

import com.bidcom.model.Usuario;
import com.bidcom.model.rolUsuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>  {
    //optional devuelve Optional.empty() si no encuentra coincidencia, 
    //evita nullPointerExeption
    Optional<Usuario> findByEmail(String email);
    
    Optional<Usuario> findByUserid(Long userid);

    List<Usuario> findByRolAndActivoTrue(rolUsuario rol);
    
    List<Usuario> findByActivoTrue();
}

