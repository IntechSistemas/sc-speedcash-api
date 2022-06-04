package br.com.intech.iseasypos.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FornecedorModel {
	private Integer id;
	private String nome;
	private String cnpj;
	private String inscricaoEstadual;
	private String inscricaoEstadualST;
	private String fone;
	private EnderecoModel endereco;
	private EmpresaIdModel empresa;
}
