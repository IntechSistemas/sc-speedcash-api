package br.com.intech.iseasypos.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeModel {
	private Integer id;
	private String codigo;
	private String descricao;
	private EstadoModel estado;
}
