package com.marbella.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.marbella.interfaces.ICliente;
import com.marbella.interfaces.IPedido;
import com.marbella.interfaces.IProducto;
import com.marbella.interfaces.IUsuario;
import com.marbella.model.Cliente;
import com.marbella.model.Pedido;
import com.marbella.model.Producto;
import com.marbella.model.TipoUsuario;
import com.marbella.model.Usuario;

import jakarta.validation.Valid;

@RestController
public class LoginController {
	@Autowired
	private IProducto servicioProducto;
	@Autowired
	private ICliente servicioCliente;
	@Autowired
	private IUsuario servicioUsuario;
	@Autowired
	private PasswordEncoder codificador;
	@Autowired
	private IPedido servicioPedido;
	
	private static int pageSize=6;
	
	@GetMapping({"/", "/index"})
	public ModelAndView index(@RequestParam(defaultValue = "1") int page) {
		Page<Producto> listaProducto = servicioProducto.listadoProductosPaginados(page-1, pageSize);
		ModelAndView m = new ModelAndView("Index");
		
		if(listaProducto != null && !listaProducto.isEmpty()) {
			m.addObject("listaPro", listaProducto.getContent());
		}
		m.addObject("currentPage", page);
	    m.addObject("totalPages", listaProducto.getTotalPages());
		return m;
	}
	
	@GetMapping("/buscarProducto")
	public ModelAndView buscarProducto(@RequestParam(value = "busqueda", required = false) String busqueda,
	                             @RequestParam(defaultValue = "1") int page) {
	    Page<Producto> listaProducto=null;
	    ModelAndView m = new ModelAndView("Index");

	    if (busqueda != null && !busqueda.trim().isEmpty()) {
	    	listaProducto = servicioProducto.buscarPorNombreOMarcaPaginado(busqueda, page-1,pageSize);
	    	m.addObject("listaPro", listaProducto.getContent());
	    } else {
	    	listaProducto = servicioProducto.listadoProductosPaginados(page-1, pageSize);
			if(listaProducto != null && !listaProducto.isEmpty()) {
				m.addObject("listaPro", listaProducto.getContent());
			}
	    }
	    
	    m.addObject("currentPage", page);
	    m.addObject("totalPages", listaProducto.getTotalPages());

	    return m; 
	}
	
	@PostMapping("/login")
	public ModelAndView processLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
		ModelAndView m = new ModelAndView("redirect:/index");
		return m;
	}
	
	@GetMapping("/login")
	public ModelAndView showLoginPage(@RequestParam(name = "error", required = false) String error, 
			@RequestParam(defaultValue = "1") int page, @AuthenticationPrincipal User usuario) {
		Page<Producto> listaProducto = servicioProducto.listadoProductosPaginados(page-1, pageSize);
		ModelAndView m = new ModelAndView("Index");
		
		if(listaProducto != null && !listaProducto.isEmpty()) {
			m.addObject("listaPro", listaProducto.getContent());
		}
		m.addObject("currentPage", page);
	    m.addObject("totalPages", listaProducto.getTotalPages());
	    
	    if (error != null && error.equals("true")) {
	        m.addObject("error", "Credenciales incorrectas, por favor intente nuevamente.");
	    } else if(usuario!=null)
	    	m.addObject("mensaje", "Bienvenido a FreshMarket, "+usuario.getUsername().toUpperCase());
	    return m; 
	}
	
	@GetMapping("/logout")
	public ModelAndView processLogoutGet() {
		ModelAndView m = new ModelAndView("redirect:/index");
	    return m;
	}
	
	@PostMapping("/registrarCliente")
	public ModelAndView registrarCliente(@Valid @ModelAttribute("cliente") Cliente c, BindingResult result, 
			@RequestParam(defaultValue = "1") int page) {    
		Page<Producto> listaProducto = servicioProducto.listadoProductosPaginados(page-1, pageSize);
		ModelAndView m = new ModelAndView("Index");
		
		if(listaProducto != null && !listaProducto.isEmpty()) {
			m.addObject("listaPro", listaProducto.getContent());
		}
		
		m.addObject("currentPage", page);
	    m.addObject("totalPages", listaProducto.getTotalPages());
	    
	    if (result.hasErrors()) {
	    	m.addObject("validationError", true);
	        return m;
	    }
	    	    
	    if (!servicioCliente.dniRepetido(c.getDniCli(), c.getCodCli())) {
	    	if(!servicioUsuario.usuarioRepetido(c.getCodUsu().getNombreUsu(),c.getCodUsu().getCodUsu())) {
	    		if (c.getCodUsu().getContrasenaUsu().equals(c.getCodUsu().getContrasenaUsu2())) {
	    			try {
			        	Usuario usu=c.getCodUsu();
			        	usu.setContrasenaUsu(codificador.encode(usu.getContrasenaUsu()));
			        	TipoUsuario tipoUsu=new TipoUsuario();
			        	tipoUsu.setCodTipoUsu(1);
			        	usu.setCodTipoUsu(tipoUsu);
			        	
			            int i = servicioCliente.grabarCliente(c);
			            String nombreUsuCli = c.getCodUsu().getNombreUsu();
			            if (i == 1) {
			                m.addObject("mensaje", "Cliente " + nombreUsuCli + " grabado con éxito");
			            } else {
			                m.addObject("error", "Error: No se pudo grabar el cliente");
			            }
			        } catch (DataIntegrityViolationException e) {
			            m.addObject("error", "Error: Problema de integridad de datos");
			        } catch (Exception e) {
			            m.addObject("error", "Error al grabar información: " + e.getMessage());
			        }
	    		}else {
	    			m.addObject("error", "Las contraseñas no coinciden");
	    		}
	    	}else {
	    		m.addObject("error", "Nombre de usuario duplicado");
	    	}   
	    } else {
	        m.addObject("error", "DNI de cliente ya registrado");
	    }

		return m;
	}
	
	@PostMapping("/cambiarContrasena")
	public ModelAndView cambiarContrasena(@RequestParam String contrasenaActual, @RequestParam String nuevaContrasena, 
	@RequestParam String nuevaContrasena2, Principal principal,@RequestParam(defaultValue = "1") int page) {
		Page<Producto> listaProducto = servicioProducto.listadoProductosPaginados(page-1, pageSize);
		ModelAndView m = new ModelAndView("Index");
		
		if(listaProducto != null && !listaProducto.isEmpty()) {
			m.addObject("listaPro", listaProducto.getContent());
		}
		
		m.addObject("currentPage", page);
	    m.addObject("totalPages", listaProducto.getTotalPages());
		String nombreUsuario = principal.getName();
		Usuario usuario = servicioUsuario.buscarPorNombreUsu(nombreUsuario).get();
		if (!codificador.matches(contrasenaActual, usuario.getContrasenaUsu())) {
			m.addObject("error", "La contraseña actual es incorrecta");
			return m;
		}
		if (!nuevaContrasena.equals(nuevaContrasena2)) {
			m.addObject("error", "Las contraseñas no coinciden");
			return m;
		}
		usuario.setContrasenaUsu(codificador.encode(nuevaContrasena));
		servicioUsuario.grabarUsuario(usuario);

		m.addObject("mensaje", "Contraseña actualizada con éxito");

		return m;
	}

	@GetMapping("/reporte")
	public ModelAndView reportes(){
		List<Cliente> clientes = servicioCliente.listadoClientes();
		List<Pedido> pedidos = servicioPedido.listadoPedidoEstadoReparto();
		List<Pedido> totalVenta = servicioPedido.listadoPedido();
		ModelAndView m = new ModelAndView("Reportes");

		if(pedidos.isEmpty()) m.addObject("totalPedido",0);
		if(clientes.isEmpty()) m.addObject("totalClient",0);
		if(totalVenta.isEmpty()) m.addObject("totalVenta",0);

		m.addObject("totalClient",clientes.size());
		m.addObject("totalPedido",pedidos.size());
		m.addObject("totalVenta",totalVenta.size());
		return m;
	}

	@GetMapping("/contactanos")
	public ModelAndView contactanos (){
		ModelAndView m = new ModelAndView("Contactanos");
		return m;
	}
}