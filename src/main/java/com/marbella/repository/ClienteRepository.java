package com.marbella.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marbella.model.Cliente;
import com.marbella.model.Usuario;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	boolean existsByDniCliAndCodCliNot(String dniCli, Integer codCli);
	Cliente getClienteByCodUsu(Usuario codUsu);
}