package com.marbella.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.marbella.model.Cliente;
import com.marbella.model.Usuario;

public interface ICliente {
	List<Cliente> listadoClientes();

	Optional<Cliente> verCliente(int id);

	int grabarCliente(Cliente a);

	boolean suprimirCliente(int id);

	Page<Cliente> listadoClientesPaginados(int page, int pageSize);

	int codUltimoCli();

	boolean dniRepetido(String dniCli, Integer codCli);

	Cliente buscarPorCodUsu(Usuario codUsu);
}