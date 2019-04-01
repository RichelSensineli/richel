package io.richel.curso.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.richel.curso.domain.Cidade;
import io.richel.curso.domain.Estado;
import io.richel.curso.dto.CidadeDTO;
import io.richel.curso.dto.EstadoDTO;
import io.richel.curso.services.CidadeService;
import io.richel.curso.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {

	@Autowired
	private EstadoService estadoService;
	
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll(){
		List<Estado> list = estadoService.findAll();
		List<EstadoDTO> listDTO = list.stream().map(objeto -> new EstadoDTO(objeto)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value="/{estadoId}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId){
		List<Cidade> list = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> listDTO = list.stream().map(objeto -> new CidadeDTO(objeto)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
	}
}
