package br.com.intech.iseasypos.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoModel {
	private Integer id;
	private Integer quantidade;
	private Integer precoUnitario;
	private Integer precoTotal;
	private ProdutoResumoModel produto;
}
