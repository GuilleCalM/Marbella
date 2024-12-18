package com.marbella.RestController;

import com.marbella.model.Cliente;
import com.marbella.model.DTO.PedidoDTO;
import com.marbella.model.Pedido;
import com.marbella.model.Usuario;
import com.marbella.service.ClienteService;
import com.marbella.service.PedidoService;
import com.marbella.service.ProductoService;
import com.marbella.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Controlador para manejar las solicitudes relacionadas con el asistente virtual watsonX.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/watson")
public class WatsonController {

    @Autowired
    private PedidoService ps;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private UsuarioService us;
    @Autowired
    private ClienteService cs;

    @PostMapping("/mispedidos")
    public ResponseEntity<?> misCompras() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }

        Usuario usuario = us.buscarPorNombreUsu(username).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Cliente cliente = cs.buscarPorCodUsu(usuario);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado para el usuario proporcionado");
        }

        List<Pedido> pedidos = ps.listadoPedidoDeCliente(cliente);

        if (pedidos == null || pedidos.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                    "mensaje", "No tienes ningún pedido"
            ));
        }

        List<PedidoDTO> pedidoDTOs = pedidos.stream()
                .map(pedido -> new PedidoDTO(
                        pedido.getCodPed(),
                        pedido.getFechaPedido(),
                        pedido.getMontoTotal(),
                        pedido.getCodEst() != null ? pedido.getCodEst().getDescripEstado() : null
                ))
                .sorted((p1, p2) -> p2.getFechaPedido().compareTo(p1.getFechaPedido()))
                .toList();

        return ResponseEntity.ok(Map.of(
                "pedidos", pedidoDTOs
        ));
    }


    @PostMapping("/pedidobyid/{id}")
    public ResponseEntity<Pedido> pedidoPorId(@PathVariable Long id){
        return null;
    }
}
