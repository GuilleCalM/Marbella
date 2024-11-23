package com.marbella.service;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marbella.interfaces.IReporte;
import com.marbella.model.Reporte;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class ReporteService implements IReporte {
    @Autowired
    private DataSource ds;

    @Override
    @Transactional(readOnly = true)
    public List<Reporte> reporteProductosMasVendidos() throws SQLException {
        List<Reporte> lista = null;
        Connection con = ds.getConnection();
        CallableStatement csm = null;
        ResultSet rs = null;
        if(con.isClosed()){
            return new ArrayList<>();
        }
        lista = new ArrayList<>();
        csm = con.prepareCall("{CALL sp_productos_mas_vendidos()}");
        rs = csm.executeQuery();
        while (rs.next()){
            lista.add(new Reporte(rs.getInt("value"), rs.getString("name")));
        }
        con.close();
        rs.close();
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reporte> reporteEstadoPedido() throws SQLException {
        List<Reporte> lista = null;
        ResultSet rs = null;
        CallableStatement csm = null;
        Connection con = ds.getConnection();

        if(con.isClosed()){
            return new ArrayList<>();
        }

        csm = con.prepareCall("{CALL sp_reporte_pedidos()}");
        rs = csm.executeQuery();
        lista = new ArrayList<>();
        while (rs.next()){
            lista.add(new Reporte(rs.getInt("value"), rs.getString("name")));
        }
        con.close();
        rs.close();
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reporte> reporteGananciaUltimaSemana() throws SQLException {
        List<Reporte> lista = null;
        ResultSet rs = null;
        CallableStatement csm = null;
        Connection con = ds.getConnection();

        if(con.isClosed()){
            return new ArrayList<>();
        }

        csm = con.prepareCall("{CALL sp_ventas_ultima_semana()}");
        rs = csm.executeQuery();
        lista = new ArrayList<>();
        while (rs.next()){
            String dia = rs.getInt("num_dia") +"";
            lista.add(new Reporte(rs.getInt("total"), dia));
        }
        con.close();
        rs.close();
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reporte> reporte10ClientesMasCompran() throws SQLException {
        List<Reporte> lista = null;
        ResultSet rs = null;
        CallableStatement csm = null;
        Connection con = ds.getConnection();

        if(con.isClosed()){
            return new ArrayList<>();
        }

        csm = con.prepareCall("{CALL sp_top_10_clientes()}");
        rs = csm.executeQuery();
        lista = new ArrayList<>();
        while (rs.next()){
            lista.add(new Reporte(rs.getInt("value"), rs.getString("name")));
        }
        con.close();
        rs.close();
        return lista;
    }

    @Transactional(readOnly = true)
    public byte[] reporteProductosStock () throws JRException, SQLException {
        InputStream jaspertStream = getClass().getResourceAsStream("/static/reportes/Reportes.jrxml");
        JasperReport jasper = JasperCompileManager.compileReport(jaspertStream);
        InputStream img = getClass().getResourceAsStream("/static/imagenes/logo1.png");
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("logoFresh", img);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameter, ds.getConnection());
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
    
    @Transactional(readOnly = true)
    public byte[] reporteProductosStockBajo () throws JRException, SQLException {
        InputStream jaspertStream = getClass().getResourceAsStream("/static/reportes/productos_stock_bajo.jrxml");
        JasperReport jasper = JasperCompileManager.compileReport(jaspertStream);
        InputStream img = getClass().getResourceAsStream("/static/imagenes/logo1.png");
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("logoFresh", img);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameter, ds.getConnection());
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}