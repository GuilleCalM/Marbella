package com.marbella.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marbella.interfaces.IHojaReparto;
import com.marbella.model.HojaReparto;
import com.marbella.model.Pedido;
import com.marbella.repository.HojaRepartoRepository;

@Service
public class HojaRepartoService implements IHojaReparto {

    @Autowired
    private HojaRepartoRepository data;
    @Autowired
    private PedidoService ps;

    @Override
    @Transactional(readOnly = true)
    public List<HojaReparto> listadoHojaReparto() {
        return data.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HojaReparto> obtenerHojaReparto(int codHoj) {
        return data.findById(codHoj);
    }

    @Override
    @Transactional
    public HojaReparto agregarHojaReparto(HojaReparto hojaReparto) {
        return data.save(hojaReparto);
    }

    @Override
    @Transactional(readOnly = true)
    public HojaReparto buscarHojaRepartoXFecha(Date fechaReparto) {
        return data.findByFechaReparto(fechaReparto);
    }

    @Override
    @Transactional
    public Page<HojaReparto> hojaRepartoEstadoReparto(int page, int pageSise) {

        List<HojaReparto> hojaRepartos = listadoHojaReparto();
        Calendar calendar = Calendar.getInstance();
        Date fechahoy = calendar.getTime();
        List<Pedido> pedidosHoy = ps.listadoPedidoXFecha(fechahoy);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date fechaMn = calendar.getTime();
        HojaReparto reparto = buscarHojaRepartoXFecha(fechaMn);

        if(reparto==null) return null;
        if(hojaRepartos.isEmpty()){
            reparto.setCantidadPed(0);
            reparto.setFechaReparto(fechaMn);
            data.save(reparto);
        }
        else{
            reparto.setCantidadPed(pedidosHoy.size());
            data.save(reparto);
        }

        PageRequest pageRequest = PageRequest.of(page, pageSise);
        return data.findAllByOrderByCodHojDesc(pageRequest);
    }
}