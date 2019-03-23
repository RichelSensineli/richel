package io.richel.curso.domain.enums;

public enum TipoCliente {
	
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private int codigo;
	private String descricao;
	
	private TipoCliente(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum(Integer id) {
		
		if (id == null) { 
			return null;
		}
		
		for (TipoCliente x : TipoCliente.values()) { 
			if (id.equals(x.getCodigo())) {
				return x; 
			}
		}
		throw new IllegalArgumentException("Id inválido " + id);
	}
}
