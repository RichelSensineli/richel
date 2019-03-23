package io.richel.curso.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.richel.curso.domain.Cliente;
import io.richel.curso.repositories.ClienteRepository;
import io.richel.curso.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> objeto = clienteRepository.findById(id);
		
		return objeto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id +", Tipo: " + Cliente.class.getName()));		
	}
}
