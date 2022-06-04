package br.com.intech.iseasypos.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteModel extends ClienteResumoModel{
	private String inscricaoEstadual;
	private String inscricaoMunicipal;
	private String fone;
	private String email;
	private EnderecoModel endereco;
	private EmpresaIdModel empresa;
	
}
