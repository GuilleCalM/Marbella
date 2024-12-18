package com.marbella.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.marbella.model.Reporte;

public interface IReporte {
    List<Reporte> reporteProductosMasVendidos() throws SQLException;
    List<Reporte> reporteEstadoPedido() throws SQLException;
    List<Reporte> reporteGananciaUltimaSemana () throws SQLException;
    List<Reporte> reporte10ClientesMasCompran () throws SQLException;
}