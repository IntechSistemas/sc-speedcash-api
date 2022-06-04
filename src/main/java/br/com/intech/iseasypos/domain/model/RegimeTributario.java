package br.com.intech.iseasypos.domain.model;

import lombok.Getter;

@Getter
public enum RegimeTributario {
	SIMPLESNACIONAL1(1, "Simples Nacional"),
	SIMPLESNACIONAL2(2, "Simples Nacional (Excesso)"),
	REGIMENORMAL(3, "Regime Normal"),
	NENHUM(4, "Nenhum");
	
	private Integer codigo;
	private String descricao;
	
	RegimeTributario(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
}





