package com.marbella.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.marbella.model.HojaReparto;

public interface IHojaReparto {
    List<HojaReparto> listadoHojaReparto ();
    Optional<HojaReparto> obtenerHojaReparto(int codHoj);
    HojaReparto agregarHojaReparto (HojaReparto hojaReparto);
    HojaReparto buscarHojaRepartoXFecha (Date fechaReparto);
    Page<HojaReparto> hojaRepartoEstadoReparto (int page, int pageSise);
}