package br.com.intech.iseasypos.domain.model;

import lombok.Getter;

@Getter
public enum StatusPagamento {
	PENDENTE(1, "Pendente"),
	PAGO(2, "Pago"),
	CANCELADO(3, "Cancelado");

	
	private Integer codigo;
	private String descricao;
	
	StatusPagamento(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
}
