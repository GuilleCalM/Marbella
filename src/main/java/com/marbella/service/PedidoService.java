package com.marbella.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marbella.interfaces.IPedido;
import com.marbella.model.Cliente;
import com.marbella.model.HojaReparto;
import com.marbella.model.Pedido;
import com.marbella.repository.PedidoRepository;

@Service
public class PedidoService implements IPedido {

    @Autowired
    private PedidoRepository data;
    @Override
    public Pedido agregarPedido(Pedido pedido) {
        return data.save(pedido);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Pedido> listadoPedidoDeClientePaginados(Cliente codCli, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return data.getPedidoByCodCliOrderByCodPedDesc(codCli,pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> listadoPedidoDeCliente(Cliente codCli) {
        return data.getPedidoByCodCliOrderByCodPedDesc(codCli);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Pedido> obtenerPedido(int codPed) {
        return data.findById(codPed);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> listadoPedido() {
        return data.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Pedido> listadoPedidoPaginados(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return data.findAllByOrderByCodPedDesc(pageRequest);
    }
    @Override
    @Transactional
    public Pedido actualizarPedido(Pedido pedido) {
       return obtenerPedido(pedido.getCodPed()).map( p ->{
            if(p.getCodPed() == pedido.getCodPed()){
                return data.save(pedido);
            }
           return null;
       }).orElse(null);

    }

    @Override
    public List<Pedido> listadoPedidoEstadoReparto() {
        return data.findPedidoByCodEst();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> listadoPedidoXFecha(Date hoy) {
        return data.findPedidoByCodEstAndFechaPedido(hoy);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> listadoPedidoXCodHoja(HojaReparto codHoj) {
        return data.getPedidoByCodHoj(codHoj);
    }


    public Pedido buscarPorId(int id) {
        return data.findById(id).orElse(null);
    }
}