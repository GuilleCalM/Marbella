package com.marbella.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.marbella.interfaces.ICategoria;
import com.marbella.interfaces.IImagen;
import com.marbella.interfaces.IMarca;
import com.marbella.interfaces.IProducto;
import com.marbella.model.Categoria;
import com.marbella.model.Marca;
import com.marbella.model.Producto;

import jakarta.validation.Valid;

@RestController
public class ProductoController {
	@Autowired
	private IMarca servicioMarca;
	@Autowired
	private ICategoria servicioCategoria;
	@Autowired
	private IProducto servicioProducto;
	@Autowired
	private IImagen servicioImagen;
	
	int pageSize=6;
	
	@GetMapping("/listarProducto")
	public ModelAndView listarProducto(@RequestParam(defaultValue = "1") int page) {
		Page<Producto> listaProducto = servicioProducto.listadoProductosPaginados(page-1,pageSize);
		ModelAndView m = new ModelAndView("CrudProducto");
		if(listaProducto != null && !listaProducto.isEmpty()) {
			m.addObject("listaPro", listaProducto.getContent());
		}else m.addObject("mensaje","No hay registros");
		
		m.addObject("currentPage", page);
	    m.addObject("totalPages", listaProducto.getTotalPages());    
		return m;
	}
	
	@GetMapping("/buscarProductoCrud")
	public ModelAndView buscarProducto(@RequestParam(value = "busqueda", required = false) String busqueda,
	                             @RequestParam(defaultValue = "1") int page) {
	    Page<Producto> listaProducto=null;
	    ModelAndView m = new ModelAndView("CrudProducto");

	    if (busqueda != null && !busqueda.trim().isEmpty()) {
	    	listaProducto = servicioProducto.buscarPorNombreOMarcaPaginado(busqueda, page-1,pageSize);
	    	m.addObject("listaPro", listaProducto.getContent());
	    } else {
	    	listaProducto = servicioProducto.listadoProductosPaginados(page-1,pageSize);
			if(listaProducto != null && !listaProducto.isEmpty()) {
				m.addObject("listaPro", listaProducto.getContent());
			}else m.addObject("mensaje","No hay registros");
	    }
	    
	    m.addObject("currentPage", page);
	    m.addObject("totalPages", listaProducto.getTotalPages());

	    return m; 
	}
	
	@GetMapping("/categoria/{idCat}")
	public ModelAndView listarProductoCategoria(@PathVariable int idCat,
			@RequestParam(defaultValue = "1") int page) {
		Page<Producto> listaProdCate = servicioProducto.listadoProdPagPorCategoria(idCat,page-1,pageSize);
		ModelAndView m = new ModelAndView("PagCategorias");
		
		if(listaProdCate != null && !listaProdCate.isEmpty()) {
			m.addObject("listaPro", listaProdCate.getContent());
		}
		Categoria categoria = servicioCategoria.buscarCategoria(idCat);
		m.addObject("titulo", categoria.getNombreCategoria());
		m.addObject("currentPage", page);
	    m.addObject("totalPages", listaProdCate.getTotalPages());
	    
		return m;
	}
	
	@GetMapping("/verOEditarProducto/{id}")
	public ModelAndView verOEditarProducto(@PathVariable int id, @RequestParam(value = "editar", required = false) boolean editar, 
			@RequestParam(name = "page") int currentPage, RedirectAttributes ra) {
	    Optional<Producto> pro = servicioProducto.verProducto(id);
	    ModelAndView m = new ModelAndView();
	    if(!pro.isPresent()) {
	    	ra.addFlashAttribute("error","Producto no existe en BD");
	    	m.setViewName("redirect:/listarProducto");
	        return m;
	    }
	    List<Categoria> listaCategorias = servicioCategoria.listadoCategoria();
	    List<Marca> listaMarcas = servicioMarca.listadoMarca();
	    m.addObject("currentPage",currentPage);
		m.addObject("listaCategorias", listaCategorias);
		m.addObject("listaMarcas", listaMarcas);
	    m.addObject("producto", pro.get());
	    m.addObject("editar", editar);
	    m.addObject("nuevo", false);
	    m.setViewName("ModificarProducto");
	    return m;
	}
	
	@GetMapping("/nuevoProducto")
	public ModelAndView agregarProducto() {
		List<Categoria> listaCategorias = servicioCategoria.listadoCategoria();
	    List<Marca> listaMarcas = servicioMarca.listadoMarca();
	    int nuevoCod = servicioProducto.codUltimoProd();
	    ModelAndView m = new ModelAndView("ModificarProducto");
	    m.addObject("listaCategorias", listaCategorias);
		m.addObject("listaMarcas", listaMarcas);
		m.addObject("producto", new Producto());
		m.addObject("editar", true);
		m.addObject("nuevo", true);
		m.addObject("nuevoCod", nuevoCod+1);
		return m;
	}
	
	@PostMapping("/salvarProducto")
	public ModelAndView grabarProducto(@Valid @ModelAttribute("producto") Producto p, BindingResult result,
	        @RequestParam(defaultValue = "1") int page, boolean nuevo,
	        @RequestParam("imagen") MultipartFile imgPro) throws Exception {
		List<Categoria> listaCategorias = servicioCategoria.listadoCategoria();
	    List<Marca> listaMarcas = servicioMarca.listadoMarca();
	    ModelAndView m = new ModelAndView("ModificarProducto");
	    m.addObject("listaCategorias", listaCategorias);
		m.addObject("listaMarcas", listaMarcas);
		m.addObject("currentPage", page);
		m.addObject("nuevo", nuevo);
		
		if(result.hasErrors()) {
			m.addObject("editar", true);
			if(nuevo==true) {
				int nuevoCod = servicioProducto.codUltimoProd();
				m.addObject("nuevoCod", nuevoCod+1);
			}
			return m;
		}

		try {
			if (!imgPro.isEmpty()) {
				String cateFormat=p.getCategoria().getNombreCategoria().toLowerCase().
						replaceAll("[á]", "a").replaceAll("[í]", "i").replaceAll("[^a-z0-9]+", "_");
				//Producto pro = servicioProducto.verProducto(p.getCodPro()).get();
	            //servicioImagen.borrarImagen(pro.getImgPro(),cateFormat);
	            String imagen = servicioImagen.subirImagen(imgPro,cateFormat);
	            p.setImgPro(imagen);
	        }else {
	            Producto productoExistente = servicioProducto.verProducto(p.getCodPro()).orElse(null);
	            if (productoExistente != null) {
	                p.setImgPro(productoExistente.getImgPro());
	            }
	        }
			int i=servicioProducto.grabarProducto(p);
			int codigoPro=p.getCodPro();
			if(i==1) {
				m.addObject("mensaje","Producto "+codigoPro+" grabado con éxito");
			}
			else 
				m.addObject("mensaje","Error: No se pudo grabar el producto");
		} catch (DataIntegrityViolationException  e) {
			m.addObject("error", "Error: Problema de integridad de datos");
		} catch (Exception e) {
			System.out.println("Error al grabar información: " + e.getMessage());
			m.addObject("error", "Error al grabar información"+e.getSuppressed());
		}
		
		m.addObject("editar", false);
		return m;
	}

	@GetMapping("/eliminarProducto/{id}")
	public ModelAndView eliminarProducto(@PathVariable int id, @RequestParam(name = "page") int currentPage, RedirectAttributes ra) {
		Optional<Producto> pro = servicioProducto.verProducto(id);
		ModelAndView m = new ModelAndView();
		try {
			if (!pro.equals(null)) {
				boolean b = servicioProducto.suprimirProducto(id);
				if(b) {
					Producto proEliminado=pro.get();
					int codigoPro = proEliminado.getCodPro();
					ra.addFlashAttribute("mensaje","Producto "+codigoPro+" eliminado");
				}
				else ra.addFlashAttribute("error","Error: El producto no existe");
			}
		} catch (Exception e) {
			ra.addFlashAttribute("error", "Error al eliminar el producto");
		}
		Page<Producto> listaProducto = servicioProducto.listadoProductosPaginados(currentPage-1,pageSize);
		if (listaProducto != null && !listaProducto.isEmpty()) {
	        m.setViewName("redirect:/listarProducto?page=" + currentPage);
	    } else {
	        m.setViewName("redirect:/listarProducto?page=" + (currentPage - 1));
	    }
	    return m;
	}
}