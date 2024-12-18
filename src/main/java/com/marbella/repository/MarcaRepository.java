package com.marbella.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marbella.model.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Integer>{
	List<Marca> getByNombreMarca(String nombreMarca);
}