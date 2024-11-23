package com.marbella.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marbella.interfaces.ITipoUsuario;
import com.marbella.model.TipoUsuario;
import com.marbella.repository.TipoUsuarioRepository;

@Service
public class TipoUsuarioService implements ITipoUsuario{

	@Autowired
	private TipoUsuarioRepository data;
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoUsuario> listadoTipoUsu() {
		return (List<TipoUsuario>) data.findAll();
	}
	
}