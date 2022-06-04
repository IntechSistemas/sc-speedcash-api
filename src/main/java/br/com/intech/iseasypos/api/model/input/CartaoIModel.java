package br.com.intech.iseasypos.api.model.input;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.intech.iseasypos.domain.model.Bandeira;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartaoIModel {
	private String autorizacao;
	private String cnpj;
	private String pos;
	private String nsu;
	@Enumerated(EnumType.STRING)
	private Bandeira bandeira;
}
