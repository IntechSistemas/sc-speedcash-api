package br.com.intech.iseasypos.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoModel {
	private Integer id;
	private String descricao;
	private Boolean ativo;
	private EmpresaIdModel empresa;
}	