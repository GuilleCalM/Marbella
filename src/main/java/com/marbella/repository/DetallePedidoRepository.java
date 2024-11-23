package com.marbella.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marbella.model.DetallePedido;
import com.marbella.model.Pedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido,Integer> {
    List<DetallePedido> getDetallePedidoByCodPed (Pedido codPed);
}