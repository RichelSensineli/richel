package io.richel.curso.dto;

import java.io.Serializable;

import io.richel.curso.domain.Cidade;

public class CidadeDTO implements Serializable{
	private static final long serialVersionUID = 1588414373132922823L;

	private Integer id;
	private String nome;
	
	public CidadeDTO() {
		
	}
	
	public CidadeDTO(Cidade objeto) {
		this.id = objeto.getId();
		this.nome = objeto.getNome();
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
}
