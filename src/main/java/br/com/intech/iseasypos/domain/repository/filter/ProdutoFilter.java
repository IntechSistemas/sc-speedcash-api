package br.com.intech.iseasypos.domain.repository.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoFilter {
	private Integer id;
	private String descricao;
	private String cean;
	private String ncm;
	private Boolean servico;
	private Boolean ativo;
	private Integer fornecedor;
	private Integer grupo;
	@JsonIgnore
	private Integer empresa;
}
