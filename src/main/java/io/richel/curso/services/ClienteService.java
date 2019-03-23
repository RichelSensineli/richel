package io.richel.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import io.richel.curso.domain.Cliente;
import io.richel.curso.dto.ClienteDTO;
import io.richel.curso.repositories.ClienteRepository;
import io.richel.curso.services.exceptions.DataIntegrityException;
import io.richel.curso.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> objeto = clienteRepository.findById(id);
		
		return objeto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id +", Tipo: " + Cliente.class.getName()));		
	}
	
	public Cliente insert(Cliente objeto) {
		objeto.setId(null);
		
		return clienteRepository.save(objeto);
	}
	
	public Cliente update(Cliente objeto) {
		Cliente newObjeto = find(objeto.getId());
		
		updateData(newObjeto, objeto);
		
		return clienteRepository.save(newObjeto);
	}

	public void delete(Integer id) {
		find(id);
		
		try {
			clienteRepository.deleteById(id);
			
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir Entidades Relacionadas.");
		}
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return clienteRepository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objetoDTO) {
		return new Cliente(objetoDTO.getId(), objetoDTO.getNome(), objetoDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObjeto, Cliente objeto) {
		newObjeto.setNome(objeto.getNome());
		newObjeto.setEmail(objeto.getEmail());
	}
}
