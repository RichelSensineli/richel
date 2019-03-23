package io.richel.curso.dto;

import java.io.Serializable;

import io.richel.curso.domain.Categoria;

public class CategoriaDTO implements Serializable{
	private static final long serialVersionUID = -3682238307410286490L;
	
	private Integer id;
	private String nome;
	
	public CategoriaDTO() {
		
	}
	
	public CategoriaDTO(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
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
