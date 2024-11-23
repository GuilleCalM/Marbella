package com.marbella.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marbella.interfaces.IBoleta;
import com.marbella.model.Boleta;
import com.marbella.model.DetallePedido;
import com.marbella.model.Pedido;
import com.marbella.repository.BoletaRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class BoletaService implements IBoleta {
    @Autowired
    private BoletaRepository data;
    
    @Override
    @Transactional(readOnly = true)
    public List<Boleta> boletaPorCodPed(Pedido codPed) {
        return data.getBoletaByCodPed(codPed);
    }
    
    @Override
    @Transactional
    public Boleta generarBoleta(Boleta boleta) {
        return data.save(boleta);
    }

    @Override
    public byte[] generarBoletas (List<DetallePedido> detallePedidos) throws JRException {
        InputStream jasStream = getClass().getResourceAsStream("/static/reportes/BoletaFresh.jrxml");
        InputStream img = getClass().getResourceAsStream("/static/imagenes/logo1.png");
        JasperReport report = JasperCompileManager.compileReport(jasStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(detallePedidos);

        Map<String, Object> params = new HashMap<>();
        params.put("logo", img);

        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params,dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    @Override
    public byte[] generarHojaReparto (List<Pedido> pedidos) throws JRException {
        InputStream jasStream = getClass().getResourceAsStream("/static/reportes/HojaRepartoFresh.jrxml");
        InputStream img = getClass().getResourceAsStream("/static/imagenes/logo1.png");
        JasperReport report = JasperCompileManager.compileReport(jasStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(pedidos);
        Map<String, Object> params = new HashMap<>();
        params.put("logo", img);
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params,dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}