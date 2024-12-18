package com.marbella.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.marbella.model.Producto;

public interface IProducto {
	List<Producto> listadoProductos();
	Page<Producto> listadoProductosPaginados(int page, int pageSize);
	Page<Producto> listadoProdPagPorCategoria(int idCat, int page, int pageSize);
	Optional<Producto> verProducto(int id);
	int grabarProducto(Producto a);
	boolean suprimirProducto(int id);
	int codUltimoProd();
	Page<Producto> buscarPorNombreOMarcaPaginado(String busqueda, int page, int pageSize);
}