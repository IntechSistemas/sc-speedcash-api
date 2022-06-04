package br.com.intech.iseasypos.api.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoIModel {
	@NotNull
	@PositiveOrZero
	private Integer quantidade;
	@NotNull
    private ProdutoIdIModel produto;
}
