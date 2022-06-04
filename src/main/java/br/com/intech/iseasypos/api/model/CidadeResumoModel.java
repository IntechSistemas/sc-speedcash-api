package br.com.intech.iseasypos.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoModel {
	private Integer id;
	private String codigo;
	private String descricao;
	private String estado;
}
