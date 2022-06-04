package br.com.intech.iseasypos.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoModel {
	private Integer id;
	private String nome;
	private String descricao;
	private String contentType;
	private Long tamanho;
	private String url;
}
