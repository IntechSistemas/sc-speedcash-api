package br.com.intech.iseasypos.domain.model;

import lombok.Getter;

@Getter
public enum Bandeira {
	MASTERCARD(1, "Master Card", "01.248.201/0001-75"),
	VISA(2, "Visa", " 31.551.765/0001-43"),
	HIPERCARD(3, "Hiper Card", "03.012.230/0001-69"),
	SODEXO(4, "Sodexo", "69.034.668/0001-56");

	
	private Integer codigo;
	private String descricao;
	private String cnpj;
	
	Bandeira(Integer codigo, String descricao, String cnpj) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.cnpj = cnpj;
	}
}