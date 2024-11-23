package com.marbella.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.marbella.model.Boleta;
import com.marbella.model.Cliente;
import com.marbella.model.DetallePedido;
import com.marbella.model.EstadoPedido;
import com.marbella.model.HojaReparto;
import com.marbella.model.Pedido;
import com.marbella.model.Usuario;
import com.marbella.service.BoletaService;
import com.marbella.service.ClienteService;
import com.marbella.service.DetallePedidoService;
import com.marbella.service.EstadoPedidoService;
import com.marbella.service.HojaRepartoService;
import com.marbella.service.PedidoService;
import com.marbella.service.UsuarioService;

import net.sf.jasperreports.engine.JRException;

@RestController
public class PedidoController {
    @Autowired
    private PedidoService ps;
    @Autowired
    private DetallePedidoService dps;
    @Autowired
    private UsuarioService us;
    @Autowired
    private ClienteService cs;
    @Autowired
    private EstadoPedidoService eps;
    @Autowired
    private BoletaService bs;
    @Autowired
    private HojaRepartoService hrs;

    int pageSise = 6;


    @GetMapping("/listarPedido")
    public ModelAndView listarPedido (@RequestParam(defaultValue = "1") int page){
        Page<Pedido> pedidos = ps.listadoPedidoPaginados(page-1,pageSise);
        List<EstadoPedido> estado = eps.listadoEstadoPedido();
        if(estado.isEmpty()) estado = new ArrayList<>();
        ModelAndView m = new ModelAndView("Pedido");

        if(pedidos != null && !pedidos.isEmpty()){
            m.addObject("listEstado",estado);
            m.addObject("listPedido", pedidos);
        }else m.addObject("mensaje", "no hay pedidos");

        m.addObject("currentPage", page);
        m.addObject("totalPages", pedidos.getTotalPages());
        return m;
    }

    @PostMapping("/actualizarEstado")
    public ModelAndView actualizarEstado (@RequestParam(name = "codPed") int codPed, @RequestParam(name = "codEst") int codEst,RedirectAttributes ra){
        Pedido pedido = ps.obtenerPedido(codPed).get();
        EstadoPedido estado = eps.obtenerEstadoPedido(codEst).get();
        ModelAndView m = new ModelAndView("redirect:/listarPedido");
        if(pedido != null &&  estado != null){
            pedido.setCodEst(estado);
            ps.actualizarPedido(pedido);
            ra.addFlashAttribute("mensaje", "Pedido se actualizo correctamente");
        }else
        	ra.addFlashAttribute("error", "Pedido no existe");
        return m;
    }

    @GetMapping("/misPedidos")
    public ModelAndView misCompras (@RequestParam(defaultValue = "1") int page, Principal principal){

        Usuario usuario = us.buscarPorNombreUsu(principal.getName()).get();
        Cliente cliente = cs.buscarPorCodUsu(usuario);

        Page<Pedido> pedidos = ps.listadoPedidoDeClientePaginados(cliente, page-1,pageSise);
        ModelAndView m = new ModelAndView("MisPedidos");
        
        if(!pedidos.isEmpty() && pedidos != null) {
            m.addObject("pedido", pedidos);

        }else m.addObject("mensaje", "No tienes ningun pedido");
        m.addObject("currentPage", page);
        m.addObject("totalPages", pedidos.getTotalPages());
        return m;
    }

    @GetMapping("/listarHoja")
    public ModelAndView listadoHojaReparto (@RequestParam(defaultValue = "1") int page){
        Page<HojaReparto> repartos = hrs.hojaRepartoEstadoReparto(page-1,pageSise);
        ModelAndView m = new ModelAndView("HojaReparto");

        if(repartos != null && !repartos.isEmpty() ){
            m.addObject("listHoja", repartos);
            m.addObject("totalPages", repartos.getTotalPages());
        }else {
        	m.addObject("mensaje", "No hay hoja Repartos");
        	m.addObject("totalpages", 1);
        }

        m.addObject("currentPage", page);

        return m;
    }

    @GetMapping("/verPedido/{codPed}")
    @ResponseBody
    public List<DetallePedido> verPedido (@PathVariable int codPed){
        Pedido pedido = ps.obtenerPedido(codPed).get();

        List<DetallePedido> listaDetalle = new ArrayList<>();
        DetallePedido data = null;

        if(pedido == null) return new ArrayList<>();

        for(DetallePedido detalle : pedido.getDetallePedido()){
            data = new DetallePedido();
            data.setMontoDet(detalle.getMontoDet());
            data.setCodPro(detalle.getCodPro());
            data.setCantidadDet(detalle.getCantidadDet());
            listaDetalle.add(data);
        }
        return listaDetalle;
    }

    @GetMapping("/obtenerBoleta/{codPed}")
    public ResponseEntity<byte[]> obtenerBoleta(@PathVariable int codPed,  Model m)throws JRException {
        Pedido p = ps.obtenerPedido(codPed).get();
        List<Boleta> boletas = bs.boletaPorCodPed(p);
        Boleta b = (boletas != null && !boletas.isEmpty()) ? boletas.get(0) : null;
        byte[] pdfBytes = null;

        if (b == null) {
            Boleta boleta = new Boleta();
            Calendar cal = Calendar.getInstance();
            Date fechaHoy = cal.getTime();
            boleta.setFechaBoleta(fechaHoy);
            boleta.setIgvFinal(Math.round(p.getMontoTotal() * 0.18 * 100.0) / 100.0);
            boleta.setMontoFinal(p.getMontoTotal());
            boleta.setCodPed(p);
            Boleta verBoleta = bs.generarBoleta(boleta);
            if (verBoleta == null) {
                m.addAttribute("error", "Error al generar boleta");
                new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
            }
            b=verBoleta;
        }
        p.setBoleta(b);
        List<DetallePedido> listaDetalle = dps.buscarDetallePorPedido(p);
        pdfBytes = bs.generarBoletas(listaDetalle);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(pdfBytes.length);
        headers.add("Content-Disposition", "inline; filename=boleta.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    @GetMapping("/generarHojaReparto/{codHoj}")
    public ResponseEntity<byte[]> generarHojaReparto (@PathVariable int codHoj, Model m) throws JRException {
        HojaReparto hojaReparto = hrs.obtenerHojaReparto(codHoj).get();
        if(hojaReparto.getCantidadPed() <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<Pedido> lista = ps.listadoPedidoXCodHoja(hojaReparto);
        List<Pedido> listaReparto = new ArrayList<>();
        for(Pedido p:lista) {
        	if(p.getCodEst().getCodEst()==3) {
        		listaReparto.add(p);
        	}
        }
        byte [] pdHoja = null;
        if(listaReparto.isEmpty()){
            m.addAttribute("error", "Error al generar Hoja Reparto");
            new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
        pdHoja = bs.generarHojaReparto(listaReparto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(pdHoja.length);
        headers.add("Content-Disposition", "inline; filename=hojaReparto.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdHoja);
    }

}