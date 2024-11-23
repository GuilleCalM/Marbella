package com.marbella.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marbella.interfaces.IUsuario;
import com.marbella.model.Usuario;
import com.marbella.repository.UsuarioRepository;

@Service
public class UsuarioService implements IUsuario {

	@Autowired
	private UsuarioRepository data;
	
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> listadoUsuarios() {
		return (List<Usuario>) data.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> listadoUsuariosPaginados(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return data.findAll(pageRequest);
    }
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Usuario> verUsuario(int id) {
		return data.findById(id);
	}

	@Override
	@Transactional
	public int grabarUsuario(Usuario u) {
		int resultado = 0;
		Usuario usu = data.save(u);
		if (!usu.equals(null))
			resultado = 1;
		return resultado;
	}

	@Override
	@Transactional
	public boolean suprimirUsuario(int id) {
		if(data.existsById(id)) {
			data.deleteById(id);
			return true;
		}else
			return false;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean usuarioRepetido(String nombreUsu, int codUsu) {
		return data.existsByNombreUsuAndCodUsuNot(nombreUsu,codUsu);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int codUltimoUsu() {
	    PageRequest pageRequest = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "codUsu"));
	    List<Usuario> usuarios = data.findAll(pageRequest).getContent();
	    
	    if (!usuarios.isEmpty()) {
	        Usuario ultimoUsuario = usuarios.get(0);
	        int codigoUltimoUsuario = ultimoUsuario.getCodUsu();
	        return codigoUltimoUsuario;
	    } else {
	        return 1000;
	    }
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Usuario> buscarPorNombreUsu(String nombreUsu){
		return data.findByNombreUsu(nombreUsu);
	}
}