package io.richel.curso.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.richel.curso.domain.Cliente;
import io.richel.curso.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteRosource {

	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id){
		
		Cliente objeto = clienteService.buscar(id);
		return ResponseEntity.ok().body(objeto);
	}
}
