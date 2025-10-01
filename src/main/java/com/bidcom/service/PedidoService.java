package com.bidcom.service;
import com.bidcom.model.Cliente;
import com.bidcom.model.Pedido;
import com.bidcom.repositories.PedidoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PedidoService extends BaseService<Pedido, PedidoRepository> {
    
    private final PedidoRepository pedidoRepository;
    
    public PedidoService(PedidoRepository pedidoRepository) {
        super(pedidoRepository);
        this.pedidoRepository = pedidoRepository;
    }
    
    @Override
    public Optional<Pedido> buscarPorLlavePrimaria(Long pedidoID) {
        return pedidoRepository.findByPedidoID(pedidoID);
    }
    
    public List<Pedido> buscarTodos() {
        return pedidoRepository.findByActivoTrue();
    }
    /**
     * Busca todos los pedidos de un cliente específico.
     * La firma se ajusta para usar el tipo Cliente, no Usuario.
     */
    public List<Pedido> buscarPedidosDeCliente(Cliente cliente){
        // El repositorio buscará la columna FK por el objeto Cliente
        return pedidoRepository.findByCliente(cliente); 
    }
}