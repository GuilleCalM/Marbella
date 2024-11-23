package com.marbella.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.marbella.model.Usuario;

public interface IUsuario {
	List<Usuario> listadoUsuarios();
	Page<Usuario> listadoUsuariosPaginados(int page, int pageSize);
	Optional<Usuario> verUsuario(int id);
	int grabarUsuario(Usuario a);
	boolean suprimirUsuario(int id);
	int codUltimoUsu();
	boolean usuarioRepetido(String nombreUsu, int codUsu);
	Optional<Usuario> buscarPorNombreUsu(String nombreUsu);
}