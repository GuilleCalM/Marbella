package com.marbella.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marbella.interfaces.ICategoria;
import com.marbella.interfaces.IProducto;
import com.marbella.model.Categoria;
import com.marbella.model.Producto;
import com.marbella.repository.ProductoRepository;

@Service
public class ProductoService implements IProducto {
	@Autowired
	private ProductoRepository data;
	
	@Autowired
	private ICategoria dataCategoria;
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> listadoProductos() {
		return (List<Producto>) data.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Producto> listadoProductosPaginados(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return data.findAll(pageRequest);
    }
	
	@Override
	@Transactional(readOnly = true)
	public Page<Producto> listadoProdPagPorCategoria(int idCat, int page, int pageSize) {
	    Categoria categoria = dataCategoria.buscarCategoria(idCat);
	    if (categoria != null) {
	        PageRequest pageRequest = PageRequest.of(page, pageSize);
	        return data.findByCategoria(categoria, pageRequest);
	    } else {
	        return Page.empty();
	    }
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Producto> buscarPorNombreOMarcaPaginado(String busqueda, int page, int pageSize) {
		PageRequest pageRequest = PageRequest.of(page, pageSize);
		if (busqueda != null && !busqueda.trim().isEmpty()) {
	        return data.findByNombreProContainingOrMarca_NombreMarcaContaining(busqueda, busqueda, pageRequest);
	    } else {
	        return data.findAll(pageRequest);
	    }
    }
	

	@Override
	@Transactional(readOnly = true)
	public Optional<Producto> verProducto(int id) {
		return data.findById(id);
	}

	@Override
	@Transactional
	public int grabarProducto(Producto p) {
		int resultado = 0;
		Producto pro = data.save(p);
		if (!pro.equals(null))
			resultado = 1;
		return resultado;
	}

	@Override
	@Transactional
	public boolean suprimirProducto(int id) {
		if(data.existsById(id)) {
			data.deleteById(id);
			return true;
		}else
			return false;
	}
	
	@Override
	@Transactional(readOnly = true)
	public int codUltimoProd() {
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "codPro"));
        List<Producto> productos = data.findAll(pageRequest).getContent();
	    
	    if (!productos.isEmpty()) {
	        Producto ultimoProducto = productos.get(0);
	        int codigoUltimoProducto = ultimoProducto.getCodPro();
	        return codigoUltimoProducto;
	    } else {
	        return 7000;
	    }
    }
}