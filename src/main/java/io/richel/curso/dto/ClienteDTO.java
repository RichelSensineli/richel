package io.richel.curso.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import io.richel.curso.domain.Cliente;

public class ClienteDTO implements Serializable{
	private static final long serialVersionUID = -3493703012586588804L;

	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=120, message="O nome deve conter entre 5 e 120 caracteres")
	private String nome;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="E-Mail inválido")
	private String email;
	
	public ClienteDTO() {
		
	}

	public ClienteDTO(Cliente objeto) {
		this.id = objeto.getId();
		this.nome = objeto.getNome();
		this.email = objeto.getEmail();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
