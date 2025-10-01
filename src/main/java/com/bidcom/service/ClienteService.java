package com.bidcom.service;

import com.bidcom.model.Cliente;
import com.bidcom.repositories.ClienteRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional; // Importación necesaria para buscarPorLlavePrimaria
import org.springframework.stereotype.Service;

@Service
// ¡IMPORTANTE! Extendemos de BaseService para heredar las funcionalidades genéricas
public class ClienteService extends BaseService<Cliente, ClienteRepository> { 
    
    // Mantenemos el atributo local para facilidad de acceso y evitar casting
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        // Llamada al constructor del padre para inicializar 'repository'
        super(clienteRepository); 
        this.clienteRepository = clienteRepository;
    }

    // --- Implementación de BaseService (Método abstracto) ---
    
    @Override
    public Optional<Cliente> buscarPorLlavePrimaria(Long userid) {
        // Asumiendo que el ID del cliente se mapea al campo userid
        return clienteRepository.findByUserid(userid); 
    }
}