package com.marbella.interfaces;

import java.util.List;

import com.marbella.model.Boleta;
import com.marbella.model.DetallePedido;
import com.marbella.model.Pedido;

import net.sf.jasperreports.engine.JRException;

public interface IBoleta {
    List<Boleta> boletaPorCodPed (Pedido codPed);
    Boleta generarBoleta (Boleta boleta);
    byte[] generarBoletas (List<DetallePedido> detallePedidos) throws JRException;
	byte[] generarHojaReparto(List<Pedido> pedidos) throws JRException;
}