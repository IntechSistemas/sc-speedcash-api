package br.com.intech.iseasypos.api.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel extends UsuarioResumoModel {
	private String login;
	private String email;
	private Boolean ativo;
	private Integer descontoMaximo;
	private EmpresaIdModel empresa;
	private List<CargoModel> cargos;
}