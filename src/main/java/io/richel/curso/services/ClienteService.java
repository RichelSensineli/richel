package io.richel.curso.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.richel.curso.domain.Cidade;
import io.richel.curso.domain.Cliente;
import io.richel.curso.domain.Endereco;
import io.richel.curso.domain.enums.Perfil;
import io.richel.curso.domain.enums.TipoCliente;
import io.richel.curso.dto.ClienteDTO;
import io.richel.curso.dto.ClienteNewDTO;
import io.richel.curso.repositories.ClienteRepository;
import io.richel.curso.repositories.EnderecoRepository;
import io.richel.curso.security.UserSS;
import io.richel.curso.services.exceptions.AuthorizationException;
import io.richel.curso.services.exceptions.DataIntegrityException;
import io.richel.curso.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated();
		
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		Optional<Cliente> objeto = clienteRepository.findById(id);
		
		return objeto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id +", Tipo: " + Cliente.class.getName()));		
	}
	
	public Cliente findByEmail(String email) {
		
		UserSS user = UserService.authenticated();
		
		if(user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			throw new ObjectNotFoundException(
				"Objeto não encontrado! id: " + user.getId() +", Tipo: " + Cliente.class.getName());
		}
		return cliente;
	}
	
	@Transactional
	public Cliente insert(Cliente objeto) {
		objeto.setId(null);
		objeto = clienteRepository.save(objeto);
		
		enderecoRepository.saveAll(objeto.getEnderecos());
		
		return objeto;  
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
		return new Cliente(objetoDTO.getId(), objetoDTO.getNome(), objetoDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objetoDTO) {
		Cliente cli = new Cliente(null, objetoDTO.getNome(), objetoDTO.getEmail(), objetoDTO.getCpfOuCnpj(), TipoCliente.toEnum(objetoDTO.getTipoCliente()), bCrypt.encode(objetoDTO.getSenha()) );
		Cidade cid = new Cidade(objetoDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objetoDTO.getLogradouro(), objetoDTO.getNumero(), objetoDTO.getComplemento(), objetoDTO.getBairro(), objetoDTO.getCep(), cli, cid);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objetoDTO.getTelefone1());
		
		if(objetoDTO.getTelefone2()!=null) {
			cli.getTelefones().add(objetoDTO.getTelefone2());
		}
		if(objetoDTO.getTelefone3()!=null) {
			cli.getTelefones().add(objetoDTO.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObjeto, Cliente objeto) {
		newObjeto.setNome(objeto.getNome());
		newObjeto.setEmail(objeto.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multiPartFile) {
		
		UserSS user = UserService.authenticated();
		
		if(user == null) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multiPartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}
