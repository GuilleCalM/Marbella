package com.marbella.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marbella.interfaces.ICliente;
import com.marbella.model.Cliente;
import com.marbella.model.Usuario;
import com.marbella.repository.ClienteRepository;

@Service
public class ClienteService implements ICliente{
	@Autowired
	private ClienteRepository data;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> listadoClientes() {
		return (List<Cliente>) data.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> listadoClientesPaginados(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return data.findAll(pageRequest);
    }

	@Override
	@Transactional(readOnly = true)
	public Optional<Cliente> verCliente(int id) {
		return data.findById(id);
	}

	@Override
	@Transactional
	public int grabarCliente(Cliente c) {
		int resultado = 0;
		Cliente cli = data.save(c);
		if (!cli.equals(null))
			resultado = 1;
		return resultado;
	}

	@Override
	@Transactional
	public boolean suprimirCliente(int id) {
		if(data.existsById(id)) {
			data.deleteById(id);
			return true;
		}else
			return false;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean dniRepetido(String dniCli, Integer codCli) {
		return data.existsByDniCliAndCodCliNot(dniCli,codCli);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int codUltimoCli() {
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "codCli"));
        List<Cliente> clientes = data.findAll(pageRequest).getContent();
	    
	    if (!clientes.isEmpty()) {
	        Cliente ultimoCliente = clientes.get(0);
	        int codigoUltimoCliente = ultimoCliente.getCodCli();
	        return codigoUltimoCliente;
	    } else {
	        return 2000;
	    }
    }
	
	@Override
	@Transactional(readOnly = true)
	public Cliente buscarPorCodUsu(Usuario codUsu) {
		return data.getClienteByCodUsu(codUsu);
	}
}