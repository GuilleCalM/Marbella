package com.marbella.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.marbella.model.DetallePedido;
import com.marbella.model.Producto;
import com.marbella.model.Usuario;
import com.marbella.service.DetallePedidoService;
import com.marbella.service.ProductoService;
import com.marbella.service.UsuarioService;

@RestController
@SessionAttributes({"listaCarrito", "total" , "favorito"})
public class DetallePedidoController {

    @Autowired
    private ProductoService ps;
    @Autowired
    private UsuarioService us;
    @Autowired
    private DetallePedidoService dps;

    @ModelAttribute ("listaCarrito")
    public List<DetallePedido> getListaCarrito(){
        return new ArrayList<DetallePedido>();
    }

    @ModelAttribute ("favorito")
    public List<Producto> getFavorito(){
        return new ArrayList<Producto>();
    }

    @ModelAttribute ("total")
    public double getTotal(){
        return 0.00;
    }

    @SuppressWarnings("unchecked")
	@PostMapping("/agregarFavorito")
    public ResponseEntity<?> agregraDeseo (@RequestParam int codPro, Model m){
        List<Producto> favoritos = (List<Producto>)m.getAttribute("favorito");
        Producto p = ps.verProducto(codPro).get();
        boolean existe = false;
        if(favoritos.isEmpty()){
            favoritos.add(p);
            m.addAttribute("favorito", favoritos);
            return ResponseEntity.ok("OK");
        }
        for(Producto pro : favoritos){
            if(pro.getCodPro() == p.getCodPro()){
                existe = true;
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        if(!existe) favoritos.add(p);
        m.addAttribute("favorito", favoritos);
        return ResponseEntity.ok("OK");
    }
    
    @SuppressWarnings("unchecked")
    @GetMapping("/listarFavoritos")
    @ResponseBody
    public List<Producto> listarFavoritos (Model m){
        List<Producto> lista = (List<Producto>)m.getAttribute("favorito");
        return lista;
    }
    
    @PostMapping("/agregarFavoritoCarrito")
    @SuppressWarnings("unchecked")
    public ResponseEntity<?> agregarFavoritoCarrito(Model m){
        List<Producto> lista = (List<Producto>)m.getAttribute("favorito");
        List<DetallePedido> detalle = (List<DetallePedido>) m.getAttribute("listaCarrito");
        DetallePedido nuevoDetalle = null;
        if(lista.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        for (Producto p : lista){
            boolean existe = false;
            for(DetallePedido dt : detalle){
                if(dt.getCodPro().getCodPro() == p.getCodPro()){
                    existe = true;
                    dt.setCantidadDet( dt.getCantidadDet() + 1);
                    dt.setMontoDet( Math.round(dt.getCantidadDet() * dt.getCodPro().getPrecioPro()*100.0)/100.0);
                }

            }
            if(!existe){
                nuevoDetalle = new DetallePedido();
                nuevoDetalle.setCodPro(p);
                nuevoDetalle.setCantidadDet(1);
                nuevoDetalle.setMontoDet(Math.round(nuevoDetalle.getCantidadDet() * p.getPrecioPro()*100.0)/100.0);
                detalle.add(nuevoDetalle);
            }
        }
        double total = Math.round(detalle.stream().mapToDouble(DetallePedido :: getMontoDet).sum()*100.0)/100.0;
        m.addAttribute("favorito", new ArrayList<Producto>());
        m.addAttribute("total", total);
        m.addAttribute("listaCarrito", detalle);
        return ResponseEntity.ok("OK");
    }


    @GetMapping("/carrito")
    public ModelAndView carrito (){
    	ModelAndView m = new ModelAndView("Carrito");
        return m;
    }
    @SuppressWarnings("unchecked")
	@PostMapping("/agregar")
    public ResponseEntity<?> agregarProductoCarrito (@RequestParam int cantidad, @RequestParam int codPro, Model m){
        List<DetallePedido> detalle = (List<DetallePedido>) m.getAttribute("listaCarrito");
        double total = (double) m.getAttribute("total");
        Producto producto = ps.verProducto(codPro).get();
        DetallePedido detallePed = null;
        boolean existe = false;
        if(producto != null){
            if(cantidad > producto.getStock()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            detallePed = new DetallePedido();
            detallePed.setCodPro(producto);
            detallePed.setCantidadDet(cantidad);
            detallePed.setMontoDet(Math.round(cantidad * producto.getPrecioPro()*100.0)/100.0);
        }

        if(!detalle.isEmpty()){
            for(DetallePedido dt : detalle){
                if(dt.getCodPro().getCodPro() == codPro){
                    if((dt.getCantidadDet() + cantidad) > producto.getStock()){
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                    dt.setCantidadDet(dt.getCantidadDet() + cantidad);
                    dt.setMontoDet(Math.round(dt.getCantidadDet() * dt.getCodPro().getPrecioPro()*100.0)/100.0);
                    existe = true;
                }
            }
            if(!existe) {
                detalle.add(detallePed);
            }
        }else detalle.add(detallePed);

        total = Math.round(detalle.stream().mapToDouble(DetallePedido :: getMontoDet).sum()*100.0)/100.0;
        m.addAttribute("total", total);
        m.addAttribute("listaCarrito", detalle);

        return  ResponseEntity.ok("OK");
    }
    
    @SuppressWarnings("unchecked")
	@PostMapping("/actualizarCarrito")
    public ModelAndView actualizarCarrito (@RequestParam(name = "codPro") int codPro, @RequestParam(name = "cantidad") int cantidad, Model model){
        List<DetallePedido> detalle = (List<DetallePedido>) model.getAttribute("listaCarrito");
        Producto producto = ps.verProducto(codPro).get();
        ModelAndView m = new ModelAndView("carrito");
        
        for(DetallePedido dt : detalle){
            if(dt.getCodPro().getCodPro() == codPro){
                if(cantidad > producto.getStock()){
                    m.addObject("error","Cantidad de Stock no disponible");
                    return m;
                }
                if(cantidad <= 0){
                    detalle.remove(dt);
                    double total = Math.round(detalle.stream().mapToDouble(DetallePedido :: getMontoDet).sum()*100.0)/100.0;
                    model.addAttribute("total", total);
                    return m;
                }

                dt.setCantidadDet(cantidad);
                dt.setMontoDet(Math.round(dt.getCodPro().getPrecioPro() * cantidad*100.0)/100.0);
            }
        }
        double total = Math.round(detalle.stream().mapToDouble(DetallePedido :: getMontoDet).sum()*100.0)/100.0;

        model.addAttribute("total", total);
        model.addAttribute("listaCarrito", detalle);
        return m;
    }
    @SuppressWarnings("unchecked")
	@GetMapping("/realizarpago")
    public ModelAndView realizarPago (Model model){
        List<DetallePedido> detalle = ( List<DetallePedido>)model.getAttribute("listaCarrito");
        ModelAndView m = new ModelAndView("carrito");
        
        if(detalle.isEmpty()){
            m.addObject("error", "tu carrito esta vacio");
        }
        return m;
    }

    @SuppressWarnings("unchecked")
	@GetMapping("/pagar")
    public ModelAndView realizarPago (Model model, Principal principal){
        List<DetallePedido> detalle = ( List<DetallePedido>)model.getAttribute("listaCarrito");
        Usuario usuario = us.buscarPorNombreUsu(principal.getName()).get();
        ModelAndView m = new ModelAndView("carrito");

        if(detalle.isEmpty()){
            m.addObject("error","Tu carrito esta vacío");
            return m;
        }

        if(dps.realizarPago(detalle, usuario)){
            m.addObject("mensaje", "Pago relizado con éxito");
            model.addAttribute("listaCarrito", new ArrayList<DetallePedido>());
            model.addAttribute("total", 0.00);
        }else m.addObject("error","ocurrio un error al realizar el pago");
        return m;
    }

    @SuppressWarnings("unchecked")
	@GetMapping("/eliminar/{id}")
    public ModelAndView eliminarDelCarrito (@PathVariable int id, Model model){
        List<DetallePedido> detalle = (List<DetallePedido>) model.getAttribute("listaCarrito");
        ModelAndView m = new ModelAndView("carrito");
        boolean existe = false;
        for(DetallePedido dt : detalle){
            if(dt.getCodPro().getCodPro() == id){
                detalle.remove(dt);
                double total = Math.round(detalle.stream().mapToDouble(DetallePedido :: getMontoDet).sum()*100.0)/100.0;
                model.addAttribute("total", total);
                m.addObject("mensaje","producto: "+ dt.getCodPro().getNombrePro() + " eliminado");
                existe = true;
                break;
            }
        }
        if(!existe) m.addObject("error","producto con codigo: "+ id + " no existe");
        return m;
    }
}