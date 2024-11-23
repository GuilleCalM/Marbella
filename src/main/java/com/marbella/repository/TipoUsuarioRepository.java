package com.marbella.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marbella.model.TipoUsuario;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer> {
    Optional<TipoUsuario> findByDescTipoUsu(String descTipoUsu);
}