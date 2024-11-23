package com.marbella.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.marbella.model.Usuario;
import com.marbella.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> userOptional = usuarioRepository.findByNombreUsu(username);
		if(userOptional.isPresent()) {
			Usuario usuario = userOptional.get();
			List<GrantedAuthority> roles = new ArrayList<>();
			roles.add(new SimpleGrantedAuthority("ROLE_"+usuario.getCodTipoUsu().getDescTipoUsu()));
			return new User(usuario.getNombreUsu(),usuario.getContrasenaUsu(),true,true,true,true,roles);
		}else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}	
	}
}
