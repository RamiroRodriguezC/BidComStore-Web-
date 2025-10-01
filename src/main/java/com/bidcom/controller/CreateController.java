package com.bidcom.controller;

import com.bidcom.model.Cliente;
import com.bidcom.model.Item;
import com.bidcom.model.Pedido;
import com.bidcom.model.Producto;
import com.bidcom.model.Usuario;
import com.bidcom.model.rolUsuario;
import com.bidcom.service.ClienteService;
import com.bidcom.service.PedidoService;
import com.bidcom.service.ProductoService;
import com.bidcom.service.UsuarioService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CreateController {

    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final PasswordEncoder passwordEncoder;


    // ✅ Inyecto todos los servicios que necesito
    public CreateController(
            ProductoService productoService,
            UsuarioService usuarioService,
            PedidoService pedidoService,
            ClienteService clienteService,
            PasswordEncoder passwordEncoder) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
        this.passwordEncoder = passwordEncoder;

    }

    // --- CRUD DE PRODUCTOS ---

    @GetMapping("/admin/productos/nuevo")
    public String mostrarFormularioCrearProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("formFragment", "create/form-producto");
        return "create";

    }

    @PostMapping("/admin/productos/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoService.guardar(producto);
        return "redirect:/admin/productos";
    }

    // --- CRUD DE USUARIOS (ADMIN) ---

    @GetMapping("/admin/usuarios/nuevo")
    public String mostrarFormularioCrearUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("forzarCliente", false);
        model.addAttribute("formFragment", "create/form-usuario");
        return "create";

    }

    /**
     * Guarda un nuevo usuario (ADMIN, REPRESENTANTE, o CLIENTE).
     * Se usa @ModelAttribute para mapear los campos del formulario.
     */
    @PostMapping("/admin/usuarios/guardar")
    @Transactional
    public String guardarUsuario(@ModelAttribute Usuario usuario) 
    {
        // Hashear la contraseña OBLIGATORIAMENTE 
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); 

        // Determinar el rol y guardar en el servicio correcto
        if (rolUsuario.CLIENTE.equals(usuario.getRol())) {
            // Si el formulario manda un cliente, casteamos a cliente

            // Usamos el constructor sin argumentos
            Cliente cliente = new Cliente(); 

            // Copiamos los campos de la superclase (Usuario)
            cliente.setEmail(usuario.getEmail());
            cliente.setPassword(usuario.getPassword()); //ya esta hasheada desde arriba
            cliente.setRol(usuario.getRol());

            clienteService.guardar(cliente);
        } else {
            // Guardar como Usuario (ADMIN o REPRESENTANTE)
            usuarioService.guardar(usuario);
        }
        //volvemos a admin/usuarios, donde podemos ver todos los users incluido el nuevo
        return "redirect:/admin/usuarios"; 
    }

    // --- CRUD DE CLIENTES (REPRESENTANTE) ---

    @GetMapping("/representante/clientes/nuevo")
    public String mostrarFormularioCrearClienteParaRepresentante(Model model) {
        Cliente cliente = new Cliente();
        cliente.setRol(rolUsuario.CLIENTE); // El Representante solo puede crear Clientes
        model.addAttribute("usuario", cliente);
        model.addAttribute("forzarCliente", true);
        model.addAttribute("formFragment", "create/form-usuario");
        return "create";
    }

    /**
     * Guarda un Cliente creado por el Representante.
     * Como desde el panel de representante solo pueden venir clientes.
     * No hace falta hacer validaciones como en admin
     */
    @PostMapping("/representante/clientes/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente) {
        // Hasheamos la contraseña
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        clienteService.guardar(cliente);
        return "redirect:/representante/clientes";
    }
    
    // --- CRUD DE PEDIDOS (REPRESENTANTE) ---

    @GetMapping("representante/pedidos/nuevo")
    public String mostrarFormularioCrearPedido(Model model) {
        Pedido pedido = new Pedido();
        pedido.setItems(List.of(new Item()));
        model.addAttribute("pedido", pedido);
        model.addAttribute("clientes", clienteService.buscarTodos()); // para el desplegable de seleccion de clientes
        model.addAttribute("productos", productoService.buscarTodos()); //para el desplegable de seleccion de productos
        model.addAttribute("formFragment", "create/form-pedido");
        return "create";
    }
    
    @PostMapping("representante/pedidos/guardar")
    @Transactional
    public String guardarPedido(@ModelAttribute Pedido pedido) {
        // Lógica de validación, fecha, estado y cálculo de ítems. ¡Muy bien!
        
        pedido.setFechaHoraCreacion(LocalDateTime.now());
        pedido.setEstado("PENDIENTE");

        //por cada item que hayamos agregado...
        for (Item item : pedido.getItems()) {
            item.setPedido(pedido);

            Long codigoProducto = item.getProducto().getCodigoProducto();

            Producto productoCompleto = productoService.buscarPorLlavePrimaria(codigoProducto)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + codigoProducto));

            // Asigna la información completa del producto (precio, etc.)
            item.setProducto(productoCompleto);
            item.setPrecio(productoCompleto.getPrecio());
        }

        pedidoService.guardar(pedido);
        return "redirect:/representante/pedidos";
    }
}