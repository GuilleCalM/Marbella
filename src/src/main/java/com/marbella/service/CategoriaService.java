package com.marbella.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marbella.interfaces.ICategoria;
import com.marbella.model.Categoria;
import com.marbella.repository.CategoriaRepository;

@Service
public class CategoriaService implements ICategoria {
	@Autowired
	private CategoriaRepository data;
	
	@Override
	@Transactional(readOnly = true)
	public List<Categoria> listadoCategoria() {
		return (List<Categoria>) data.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Categoria buscarCategoria(int id) {
		return data.findById(id).orElse(null);
	}
}