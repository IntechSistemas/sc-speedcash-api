package br.com.intech.iseasypos.api.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoModel {
	private Integer id;
	private Integer cProd;
	private String descricao;
	private String cean;
	private String ncm;
	private String cest;
	private Integer quantidade;
	private Integer valorCompra;
	private Integer valorVenda;
	private Integer margemLucro;
	private Boolean servico;
	private Boolean ativo;
	private FornecedorIdModel fornecedor;
	private GrupoIdModel grupo;
	private UnidadeIdModel unidade;
	private List<FotoProdutoModel> catalogo;
}