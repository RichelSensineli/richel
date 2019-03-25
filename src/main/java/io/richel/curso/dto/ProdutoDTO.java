package io.richel.curso.dto;

import java.io.Serializable;

import io.richel.curso.domain.Produto;

public class ProdutoDTO implements Serializable{
	private static final long serialVersionUID = 8845167664915083123L;
	
	private Integer id;
	private String nome;
	private Double preco;

	public ProdutoDTO() {
		
	}

	public ProdutoDTO(Produto objeto) {
		this.id = objeto.getId();
		this.nome = objeto.getNome();
		this.preco = objeto.getPreco();
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	
}
