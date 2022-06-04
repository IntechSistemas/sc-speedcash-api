package br.com.intech.iseasypos.api.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoModel {
	private Integer id;
	private String descricao;
	private EmpresaIdModel empresa;
	private List<PermissaoModel> permissoes;
}
