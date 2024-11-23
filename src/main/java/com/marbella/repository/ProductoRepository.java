package com.marbella.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.marbella.model.Categoria;
import com.marbella.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	Page<Producto> findByCategoria(Categoria categoria, Pageable pageable);
	Page<Producto> findByNombreProContainingOrMarca_NombreMarcaContaining(String nombre,String marca, Pageable pageable);
}