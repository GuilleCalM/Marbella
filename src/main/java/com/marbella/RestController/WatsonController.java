package com.marbella.RestController;

import com.marbella.model.Cliente;
import com.marbella.model.DTO.PedidoDTO;
import com.marbella.model.DTO.ProductoDTO;
import com.marbella.model.DTO.UsernameDTO;
import com.marbella.model.Pedido;
import com.marbella.model.Producto;
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
import java.util.ArrayList;
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
    public ResponseEntity<?> misCompras(@RequestBody UsernameDTO username) {
        /*String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }*/

        Usuario usuario = us.buscarPorNombreUsu(username.getUsername()).orElse(null);
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
    public ResponseEntity<?> pedidoPorId(@PathVariable int id) {
        Pedido pedido = ps.buscarPorId(id);
        if (pedido == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado");
        }

        PedidoDTO pedidoDTO = new PedidoDTO(
                pedido.getCodPed(),
                pedido.getFechaPedido(),
                pedido.getMontoTotal(),
                pedido.getCodEst() != null ? pedido.getCodEst().getDescripEstado() : null
        );

        return ResponseEntity.ok(pedidoDTO);
    }

    @PostMapping("/producto/{id}")
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable int id) {
        Producto producto = productoService.obtenerProductoPorId(id);

        if (producto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }

        ProductoDTO productoDTO = new ProductoDTO(
                producto.getCodPro(),
                producto.getNombrePro(),
                producto.getDescripcionPro(),
                producto.getPrecioPro(),
                producto.getMarca() != null ? producto.getMarca().getNombreMarca() : null,
                producto.getCategoria() != null ? producto.getCategoria().getNombreCategoria() : null,
                producto.getStock()
        );

        return ResponseEntity.ok(productoDTO);
    }

    @PostMapping("/productos/buscar/{busqueda}")
    public ResponseEntity<?> buscarProducto(@PathVariable String busqueda) {
        List<Producto> listaProducto = new ArrayList<>();

        if (busqueda != null && !busqueda.trim().isEmpty()) {
            listaProducto = productoService.buscarPorNombreOMarca(busqueda);
        }

        if (listaProducto == null || listaProducto.isEmpty()) {
            return ResponseEntity.ok(Map.of("mensaje", "No se encontraron productos"));
        }

        List<ProductoDTO> productosDTO = listaProducto.stream()
                .map(producto -> new ProductoDTO(
                        producto.getCodPro(),
                        producto.getNombrePro(),
                        producto.getDescripcionPro(),
                        producto.getPrecioPro(),
                        producto.getMarca() != null ? producto.getMarca().getNombreMarca() : null,
                        producto.getCategoria() != null ? producto.getCategoria().getNombreCategoria() : null,
                        producto.getStock()
                ))
                .toList();

        return ResponseEntity.ok(Map.of("productos", productosDTO));
    }

}
