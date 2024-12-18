package com.marbella.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marbella.model.Reporte;
import com.marbella.service.ReporteService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/report")
public class ReporteController {
    @Autowired
    private ReporteService rs;

    @GetMapping("/masvendido")
    public List<Reporte> reporteProductoMasVendido () throws SQLException {
        return rs.reporteProductosMasVendidos();
    }

    @GetMapping("/semana")
    public List<Reporte> reporteGananciaUltimaSemana () throws SQLException{
        return rs.reporteGananciaUltimaSemana();
    }
    @GetMapping("/estpedido")
    public List<Reporte> reportePedidoEstado () throws SQLException {
        return rs.reporteEstadoPedido();
    }
    @GetMapping("/clientesCompra")
    public List<Reporte> reporteClientesMasCompran () throws SQLException {
        return rs.reporte10ClientesMasCompran();
    }
    @GetMapping("/productoStock")
    public ResponseEntity<byte[]> reportePoroductoStock () throws JRException, SQLException {

        byte [] pdf = rs.reporteProductosStock();
        if(pdf == null) return new ResponseEntity<>(pdf, HttpStatus.BAD_REQUEST);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(pdf.length);
        headers.add("Content-Disposition", "inline; filename=stockProducto.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);

    }
    @GetMapping("/productoStockBajo")
    public ResponseEntity<byte[]> reportePoroductoStockbajo () throws JRException, SQLException {

        byte [] pdf = rs.reporteProductosStockBajo();
        if(pdf == null) return new ResponseEntity<>(pdf, HttpStatus.BAD_REQUEST);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(pdf.length);
        headers.add("Content-Disposition", "inline; filename=stockProducto.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }
}