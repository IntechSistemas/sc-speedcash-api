package br.com.intech.iseasypos.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoModel {
	private Integer id;
	private String descricao;
	private String sigla;
	private String codigo;
}
