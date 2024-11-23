package com.marbella.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marbella.interfaces.IDetallePedido;
import com.marbella.model.Cliente;
import com.marbella.model.DetallePedido;
import com.marbella.model.EstadoPedido;
import com.marbella.model.HojaReparto;
import com.marbella.model.Pedido;
import com.marbella.model.Producto;
import com.marbella.model.Usuario;
import com.marbella.repository.DetallePedidoRepository;

@Service
public class DetallePedidoService implements IDetallePedido {

    @Autowired
    private DetallePedidoRepository dtr;
    @Autowired
    private HojaRepartoService hrs;
    @Autowired
    private PedidoService ps;
    @Autowired
    private ClienteService cs;
    @Autowired
    private EstadoPedidoService eps;
    @Autowired
    private ProductoService productoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean realizarPago(List<DetallePedido> detalle, Usuario usuario) {

        Calendar calendar = Calendar.getInstance();
        // Suma un día
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date fechaMn = calendar.getTime();
        HojaReparto hoja = null;
        HojaReparto hojaReparto = hrs.buscarHojaRepartoXFecha(fechaMn);

        if(hojaReparto == null){
            hoja = new HojaReparto();
            hoja.setCantidadPed(0);
            hoja.setFechaReparto(fechaMn);
            hojaReparto = hoja;
            hrs.agregarHojaReparto(hoja);
        }
        try {
            Cliente cliente = cs.buscarPorCodUsu(usuario);
            //agregar pedido
            EstadoPedido estado = eps.obtenerEstadoPedido(1).get();
            Pedido pedido = new Pedido();
            pedido.setCodCli(cliente);
            pedido.setFechaPedido(new Date());
            pedido.setCodHoj(hojaReparto);
            pedido.setCodEst(estado);
            pedido.setMontoTotal(Math.round(detalle.stream().mapToDouble(DetallePedido :: getMontoDet).sum()*100.0)/100.0);
            ps.agregarPedido(pedido);
            //agregra detalle Carrito
            for(DetallePedido dt : detalle){
                dt.setCodPed(pedido);
                dtr.save(dt);
            }

            //Restar stock a los pedidos
            for(DetallePedido dt: detalle){
                Producto producto = productoService.verProducto(dt.getCodPro().getCodPro()).get();
                producto.setStock( producto.getStock() - dt.getCantidadDet());
                productoService.grabarProducto(producto);
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetallePedido> buscarDetallePorPedido(Pedido codPed) {
        return dtr.getDetallePedidoByCodPed(codPed);
    }


}