package com.marbella.interfaces;

import java.util.List;

import com.marbella.model.Categoria;

public interface ICategoria {
	List<Categoria> listadoCategoria();

	Categoria buscarCategoria(int id);
}