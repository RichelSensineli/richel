package io.richel.curso.dto;

import java.io.Serializable;

import io.richel.curso.domain.Estado;

public class EstadoDTO implements Serializable{
	private static final long serialVersionUID = -4841487747182681041L;

	private Integer id;
	private String nome;
	
	public EstadoDTO () {
		
	}
	
	public EstadoDTO(Estado objeto) {
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
