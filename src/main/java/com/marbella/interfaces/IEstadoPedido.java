package com.marbella.interfaces;

import java.util.List;
import java.util.Optional;

import com.marbella.model.EstadoPedido;

public interface IEstadoPedido {
    List<EstadoPedido> listadoEstadoPedido();
    Optional<EstadoPedido> obtenerEstadoPedido(int codEst);
}