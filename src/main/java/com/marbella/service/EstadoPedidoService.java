package com.marbella.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marbella.interfaces.IEstadoPedido;
import com.marbella.model.EstadoPedido;
import com.marbella.repository.EstadoPedidoRepository;

@Service
public class EstadoPedidoService implements IEstadoPedido {
    @Autowired
    private EstadoPedidoRepository data;

    @Override
    @Transactional(readOnly = true)
    public List<EstadoPedido> listadoEstadoPedido() {
        return data.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoPedido> obtenerEstadoPedido(int codEst) {
        return data.findById(codEst);
    }
}