package io.richel.curso.domain.enums;

public enum EstadoPagamento {

	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private int codigo;
	private String descricao;
	
	private EstadoPagamento(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static EstadoPagamento toEnum(Integer id) {
		
		if (id == null) { 
			return null;
		}
		
		for (EstadoPagamento x : EstadoPagamento.values()) { 
			if (id.equals(x.getCodigo())) {
				return x; 
			}
		}
		throw new IllegalArgumentException("Id inválido " + id);
	}
}
