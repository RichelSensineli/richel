package io.richel.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import io.richel.curso.domain.Categoria;
import io.richel.curso.dto.CategoriaDTO;
import io.richel.curso.repositories.CategoriaRepository;
import io.richel.curso.services.exceptions.DataIntegrityException;
import io.richel.curso.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	public Categoria find(Integer id) {
		Optional<Categoria> objeto = repository.findById(id);
		
		return objeto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria objeto) {
		objeto.setId(null);
		
		return repository.save(objeto);
	}
	
	public Categoria update(Categoria objeto) {
		find(objeto.getId());
		
		return repository.save(objeto);
	}

	public void delete(Integer id) {
		find(id);
		
		try {
			repository.deleteById(id);
			
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Categoria que possui Produtos.");
		}
	}

	public List<Categoria> findAll() {
		return repository.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return repository.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objetoDTO) {
		return new Categoria(objetoDTO.getId(), objetoDTO.getNome());
	}
}
