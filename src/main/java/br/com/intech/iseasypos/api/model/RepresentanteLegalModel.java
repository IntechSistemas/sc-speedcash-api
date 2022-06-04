package br.com.intech.iseasypos.api.model;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class RepresentanteLegalModel {
	private Integer id;
	private String nome;
	private String cpf;
	private String email;
	private String fone;
}
