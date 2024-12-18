package com.marbella.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.marbella.model.Cliente;
import com.marbella.model.Usuario;

@ControllerAdvice
public class PrincipalControllerAdvice {

    @ModelAttribute("cliente")
    public Cliente getDefaultCliente() {
        return new Cliente();
    }
    
    @ModelAttribute("usuario")
    public Usuario getDefaultUsuario() {
    	return new Usuario();
    }
    
	@ModelAttribute("total")
	public double getDefaultTotal() {
		return 0.0;
	}
}