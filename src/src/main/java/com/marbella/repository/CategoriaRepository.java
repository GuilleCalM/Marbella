package com.marbella.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marbella.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}