package io.richel.curso.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import io.richel.curso.domain.Cliente;
import io.richel.curso.domain.enums.TipoCliente;
import io.richel.curso.dto.ClienteNewDTO;
import io.richel.curso.repositories.ClienteRepository;
import io.richel.curso.resources.exception.FieldMessage;
import io.richel.curso.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert,ClienteNewDTO> {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objetoDTO, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if(objetoDTO.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !BR.isValidCPF(objetoDTO.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		if(objetoDTO.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !BR.isValidCNPJ(objetoDTO.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente aux = clienteRepository.findByEmail(objetoDTO.getEmail());
		
		if(aux != null) {
			list.add(new FieldMessage("email", "E-Mail já cadastrado"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
			.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}
}
