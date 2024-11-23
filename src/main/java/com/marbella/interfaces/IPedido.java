package com.marbella.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.marbella.model.Cliente;
import com.marbella.model.HojaReparto;
import com.marbella.model.Pedido;

public interface IPedido {
    Pedido agregarPedido (Pedido pedido);
    Page<Pedido>listadoPedidoDeClientePaginados(Cliente codCli, int page, int pageSize);
    Optional<Pedido> obtenerPedido (int codPed);
    List<Pedido> listadoPedido ();
    Page<Pedido> listadoPedidoPaginados (int page, int pageSize);
    Pedido actualizarPedido ( Pedido pedido);
    List<Pedido> listadoPedidoEstadoReparto();
    List<Pedido> listadoPedidoXFecha (Date hoy);
    List<Pedido> listadoPedidoXCodHoja ( HojaReparto codHoj);
}