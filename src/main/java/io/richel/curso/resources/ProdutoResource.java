package io.richel.curso.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.richel.curso.domain.Pedido;
import io.richel.curso.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class ProdutoResource {

	@Autowired
	private PedidoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> listar(@PathVariable Integer id) {
		
		Pedido objeto = service.find(id);
		return ResponseEntity.ok().body(objeto);
	}
}
