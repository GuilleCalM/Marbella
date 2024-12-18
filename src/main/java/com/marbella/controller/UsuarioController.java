package com.marbella.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.marbella.interfaces.ITipoUsuario;
import com.marbella.interfaces.IUsuario;
import com.marbella.model.TipoUsuario;
import com.marbella.model.Usuario;

import jakarta.validation.Valid;

@Controller
public class UsuarioController {
	@Autowired
	private IUsuario servicioUsuario;
	@Autowired
	private ITipoUsuario servicioTipoUsu;
	@Autowired
	private PasswordEncoder codificador;
	
	int pageSize=6;
	
	@GetMapping("/listarUsuario")
	public ModelAndView listarUsuario(@RequestParam(defaultValue = "1") int page) {
		Page<Usuario> listaUsuarios = servicioUsuario.listadoUsuariosPaginados(page-1,pageSize);
		ModelAndView m = new ModelAndView("CrudUsuario");
		if(listaUsuarios != null && !listaUsuarios.isEmpty()) {
			m.addObject("listaUsu", listaUsuarios.getContent());
		}else m.addObject("mensaje","No hay registros");
		
		m.addObject("currentPage", page);
	    m.addObject("totalPages", listaUsuarios.getTotalPages());

		return m;
	}
	
	@GetMapping("/verOEditarUsuario/{id}")
	public ModelAndView verOEditarUsuario(@PathVariable int id, @RequestParam(value = "editar", required = false) boolean editar, 
			@RequestParam(name = "page") int currentPage, RedirectAttributes ra) {
	    Optional<Usuario> usu = servicioUsuario.verUsuario(id);
	    ModelAndView m = new ModelAndView();
	    if(!usu.isPresent()) {
	    	ra.addFlashAttribute("error","Usuario no existe en BD");
	    	m.setViewName("redirect:/listarUsuario");
	    	return m;
	    }
	    List<TipoUsuario> listaTipoUsuarios = servicioTipoUsu.listadoTipoUsu();
	    m.addObject("currentPage",currentPage);
		m.addObject("listaTipoUsu", listaTipoUsuarios);
	    m.addObject("usuario", usu.get());
	    m.addObject("editar", editar);
	    m.addObject("nuevo", false);
	    m.setViewName("ModificarUsuario");
	    return m;
	}
	
	@GetMapping("/nuevoUsuario")
	public ModelAndView agregarUsuario() {
		List<TipoUsuario> listaTipoUsuarios = servicioTipoUsu.listadoTipoUsu();
		int nuevoCod = servicioUsuario.codUltimoUsu();
		ModelAndView m = new ModelAndView("ModificarUsuario");
		m.addObject("listaTipoUsu", listaTipoUsuarios);
		m.addObject("usuario", new Usuario());
		m.addObject("editar", true);
		m.addObject("nuevo", true);
		m.addObject("nuevoCod", nuevoCod+1);
		return m;
	}

	@PostMapping("/salvarUsuario")
	public ModelAndView grabarUsuario(@Valid @ModelAttribute Usuario u, BindingResult result,
	        @RequestParam(defaultValue = "1") int page, boolean nuevo) {
	    List<TipoUsuario> listaTipoUsuarios = servicioTipoUsu.listadoTipoUsu();
	    ModelAndView m = new ModelAndView("ModificarUsuario");
	    m.addObject("currentPage", page);
	    m.addObject("listaTipoUsu", listaTipoUsuarios);
	    m.addObject("nuevo", nuevo);
	    m.addObject("editar", true);
	    if(nuevo==true) {
			int nuevoCod = servicioUsuario.codUltimoUsu();
			m.addObject("nuevoCod", nuevoCod+1);
		}
	    
	    if (result.hasErrors()) {
	        return m;
	    }
	    
	    if (!servicioUsuario.usuarioRepetido(u.getNombreUsu(),u.getCodUsu())) {
	    	 try {
	    		u.setContrasenaUsu(codificador.encode(u.getContrasenaUsu()));
	 	        int i = servicioUsuario.grabarUsuario(u);
	 	        int codigoUsu = u.getCodUsu();
	 	        if (i == 1) {
	 	            m.addObject("mensaje", "Usuario " + codigoUsu + " grabado con éxito");
	 	            m.addObject("editar", false);
	 	        } else {
	 	            m.addObject("error", "Error: No se pudo grabar el usuario");
	 	        }
	 	    } catch (DataIntegrityViolationException e) {
	 	        m.addObject("error", "Error: Problema de integridad de datos");
	 	    } catch (Exception e) {
	 	        m.addObject("error", "Error al grabar información");
	 	    }
	    }else {
	    	m.addObject("error", "Nombre de usuario duplicado");
	    }
  
	    return m;
	}


	@GetMapping("/eliminarUsuario/{id}")
	public ModelAndView eliminarUsuario(@PathVariable int id, @RequestParam(name = "page") int currentPage, RedirectAttributes ra) {
		Optional<Usuario> usu = servicioUsuario.verUsuario(id);
		ModelAndView m = new ModelAndView();
		try {
			if (!usu.equals(null)) {
				boolean b = servicioUsuario.suprimirUsuario(id);
				if(b) {
					Usuario usuEliminado=usu.get();
					int codigoUsu = usuEliminado.getCodUsu();
					ra.addFlashAttribute("mensaje","Usuario "+codigoUsu+" eliminado");
				}
				else ra.addFlashAttribute("error","Error: El usuario no existe");
			}
		} catch (Exception e) {
			ra.addFlashAttribute("error", "Error al eliminar el usuario");
		}
		Page<Usuario> listaUsuarios = servicioUsuario.listadoUsuariosPaginados(currentPage-1,pageSize);
		if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
	        m.setViewName("redirect:/listarUsuario?page=" + currentPage);
	    } else {
	        m.setViewName("redirect:/listarUsuario?page=" + (currentPage - 1));
	    }
	    return m;
	}
}