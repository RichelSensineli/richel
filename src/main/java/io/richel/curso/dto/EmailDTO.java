package io.richel.curso.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailDTO implements Serializable{
	private static final long serialVersionUID = 5024526720668596121L;

	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="E-Mail inválido")
	private String email;
	
	public EmailDTO() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
