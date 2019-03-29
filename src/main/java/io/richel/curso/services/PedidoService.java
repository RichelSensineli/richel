package io.richel.curso.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.richel.curso.domain.Cliente;
import io.richel.curso.domain.ItemPedido;
import io.richel.curso.domain.PagamentoComBoleto;
import io.richel.curso.domain.Pedido;
import io.richel.curso.domain.enums.EstadoPagamento;
import io.richel.curso.repositories.ClienteRepository;
import io.richel.curso.repositories.ItemPedidoRepository;
import io.richel.curso.repositories.PagamentoRepository;
import io.richel.curso.repositories.PedidoRepository;
import io.richel.curso.security.UserSS;
import io.richel.curso.services.exceptions.AuthorizationException;
import io.richel.curso.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> objeto = repository.findById(id);
		
		return objeto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido objeto) {
		objeto.setId(null);
		objeto.setInstante(new Date());
		objeto.setCliente(clienteService.find(objeto.getCliente().getId()));
		objeto.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		objeto.getPagamento().setPedido(objeto);
		
		if(objeto.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) objeto.getPagamento();
			boletoService.preencherPagementoComBoleto(pgto, objeto.getInstante());
		}
		
		objeto = repository.save(objeto);
		pagamentoRepository.save(objeto.getPagamento());
		
		for(ItemPedido ip : objeto.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(objeto);
		}
		
		itemPedidoRepository.saveAll(objeto.getItens());
		
		emailService.sendOrderConfirmationHtmlEmail(objeto);
		
		return objeto;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		UserSS user = UserService.authenticated();
		
		if(user == null) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		
		return repository.findByCliente(cliente, pageRequest);
	}
}
