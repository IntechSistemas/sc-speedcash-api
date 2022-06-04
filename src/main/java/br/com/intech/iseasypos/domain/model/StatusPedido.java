package br.com.intech.iseasypos.domain.model;

import lombok.Getter;

@Getter
public enum StatusPedido {
	PENDENTE(1, "Pendente"),
	CONFIRMADO(2, "Confirmado"),
	CANCELADO(3, "Cancelado");

	
	private Integer codigo;
	private String descricao;
	
	StatusPedido(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
}