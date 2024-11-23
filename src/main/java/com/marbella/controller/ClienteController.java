package com.marbella.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.marbella.interfaces.ICliente;
import com.marbella.interfaces.IUsuario;
import com.marbella.model.Cliente;
import com.marbella.model.Usuario;

import jakarta.validation.Valid;

@RestController
public class ClienteController {
	@Autowired
	private ICliente servicioCliente;
	@Autowired
	private IUsuario servicioUsuario;
	@Autowired
	private PasswordEncoder codificador;
	
	int pageSize=6;
	
	@GetMapping("/listarCliente")
	public ModelAndView listarCliente(@RequestParam(defaultValue = "1") int page) {
		Page<Cliente> listaClientes = servicioCliente.listadoClientesPaginados(page-1,pageSize);
		ModelAndView m = new ModelAndView("CrudCliente");
		
		if(listaClientes != null && !listaClientes.isEmpty()) {
			m.addObject("listaCli", listaClientes.getContent());
		}else m.addObject("mensaje","No hay registros");
		
		m.addObject("currentPage", page);
	    m.addObject("totalPages", listaClientes.getTotalPages());
	    
		return m;
	}
	
	@GetMapping("/verOEditarCliente/{id}")
	public ModelAndView verOEditarCliente(@PathVariable int id, @RequestParam(value = "editar", required = false) boolean editar, 
			@RequestParam(name = "page") int currentPage, RedirectAttributes ra) {
		ModelAndView m = new ModelAndView("ModificarCliente");
	    Optional<Cliente> cli = servicioCliente.verCliente(id);
	    if(!cli.isPresent()) {
	    	ra.addFlashAttribute("error","Cliente no existe en BD");
	    	return new ModelAndView("redirect:/listarCliente");
	    }
	    m.addObject("currentPage",currentPage);
	    m.addObject("cliente", cli.get());
	    m.addObject("editar", editar);
	    m.addObject("nuevo", false);
	    return m;
	}
	
	@GetMapping("/nuevoCliente")
	public ModelAndView agregarCliente() {
		int nuevoCod = servicioCliente.codUltimoCli();
		ModelAndView m = new ModelAndView("ModificarCliente");
		m.addObject("cliente", new Cliente());
		m.addObject("editar", true);
		m.addObject("nuevo", true);
		m.addObject("nuevoCod", nuevoCod+1);
		return m;
	}

	@PostMapping("/salvarCliente")
	public ModelAndView grabarCliente(@Valid @ModelAttribute("cliente") Cliente c, BindingResult result,
	        @RequestParam(defaultValue = "1") int page, boolean nuevo) {
		ModelAndView m = new ModelAndView("ModificarCliente");
	    m.addObject("currentPage", page);
	    m.addObject("nuevo", nuevo);
        m.addObject("editar", true);
        if (nuevo) {
            int nuevoCod = servicioCliente.codUltimoCli();
            m.addObject("nuevoCod", nuevoCod + 1);
        }
        
	    if (result.hasErrors()) {
	        return m;
	    }
	    	    
	    if (!servicioCliente.dniRepetido(c.getDniCli(), c.getCodCli())) {
	    	if(!servicioUsuario.usuarioRepetido(c.getCodUsu().getNombreUsu(),c.getCodUsu().getCodUsu())) {
	    		try {
		        	Usuario usu=c.getCodUsu();
		        	usu.getCodTipoUsu().setCodTipoUsu(1);
		        	usu.setContrasenaUsu(codificador.encode(usu.getContrasenaUsu()));
		            int i = servicioCliente.grabarCliente(c);
		            int codigoCli = c.getCodCli();
		            if (i == 1) {
		                m.addObject("mensaje", "Cliente " + codigoCli + " grabado con éxito");
		        	    m.addObject("editar", false);
		            } else {
		                m.addObject("error", "Error: No se pudo grabar el cliente");
		            }
		        } catch (DataIntegrityViolationException e) {
		            m.addObject("error", "Error: Problema de integridad de datos");
		        } catch (Exception e) {
		            m.addObject("error", "Error al grabar información: " + e.getMessage());
		        }
	    	}else {
	    		m.addObject("error", "Nombre de usuario duplicado");
	    	}   
	    } else {
	        m.addObject("error", "DNI de cliente ya registrado");
	    }

	    return m;
	}

	@GetMapping("/eliminarCliente/{id}")
	public ModelAndView eliminarCliente(@PathVariable int id, @RequestParam(name = "page") int currentPage, RedirectAttributes ra) {
		ModelAndView m = new ModelAndView("redirect:/listarCliente");
		Optional<Cliente> cli = servicioCliente.verCliente(id);
		try {
			if (!cli.equals(null)) {
				boolean b = servicioCliente.suprimirCliente(id);
				if(b) {
					Cliente cliEliminado=cli.get();
					int codigoCli = cliEliminado.getCodCli();
					ra.addFlashAttribute("mensaje","Cliente "+codigoCli+" eliminado");
				}
				else ra.addFlashAttribute("error","Error: El cliente no existe");
			}
		} catch (Exception e) {
			ra.addFlashAttribute("error", "Error al eliminar el cliente");
		}
		Page<Cliente> listaClientes = servicioCliente.listadoClientesPaginados(currentPage-1,pageSize);
		if (listaClientes != null && !listaClientes.isEmpty()) {
            m.addObject("page", currentPage);
        } else {
            m.addObject("page", currentPage - 1);
        }

        return m;
	}
}