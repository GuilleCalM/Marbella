package com.marbella.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marbella.interfaces.IMarca;
import com.marbella.model.Marca;
import com.marbella.repository.MarcaRepository;

@Service
public class MarcaService implements IMarca {
	@Autowired
	private MarcaRepository data;
	
	@Override
	public List<Marca> listadoMarca() {
		return (List<Marca>) data.findAll();
	}
	
	@Override
	public List<Marca> buscarMarcaPorNombre(String marca) {
		return data.getByNombreMarca(marca);
	}
}