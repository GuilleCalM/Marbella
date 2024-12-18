package com.marbella.interfaces;

import java.util.List;

import com.marbella.model.Marca;

public interface IMarca {
	List<Marca> listadoMarca();
	List<Marca> buscarMarcaPorNombre(String marca);
}