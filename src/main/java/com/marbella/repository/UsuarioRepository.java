package com.marbella.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marbella.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	boolean existsByNombreUsuAndCodUsuNot(String nombreUsuario,int codUsu);
	Optional<Usuario> findByNombreUsu(String nombreUsu);
}