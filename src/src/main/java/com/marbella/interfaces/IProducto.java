package com.marbella.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.marbella.model.Producto;
import org.springframework.transaction.annotation.Transactional;

public interface IProducto {
	List<Producto> listadoProductos();
	Page<Producto> listadoProductosPaginados(int page, int pageSize);
	Page<Producto> listadoProdPagPorCategoria(int idCat, int page, int pageSize);

    @Transactional(readOnly = true)
    List<Producto> buscarPorNombreOMarca(String busqueda);

    Optional<Producto> verProducto(int id);
	int grabarProducto(Producto a);
	boolean suprimirProducto(int id);
	int codUltimoProd();
	Page<Producto> buscarPorNombreOMarcaPaginado(String busqueda, int page, int pageSize);
}