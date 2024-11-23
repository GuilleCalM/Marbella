package com.marbella.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marbella.model.EstadoPedido;

public interface EstadoPedidoRepository extends JpaRepository<EstadoPedido, Integer> {
}