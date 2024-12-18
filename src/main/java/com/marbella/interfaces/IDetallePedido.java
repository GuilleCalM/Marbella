package com.marbella.interfaces;

import java.util.List;

import com.marbella.model.DetallePedido;
import com.marbella.model.Pedido;
import com.marbella.model.Usuario;

public interface IDetallePedido {
    boolean realizarPago (List<DetallePedido> detalle, Usuario usuario);
    List<DetallePedido> buscarDetallePorPedido (Pedido codPed);
}