package br.com.intech.iseasypos.api.model.input;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoIModel {
	@PositiveOrZero
	@Max(100)
	private Integer desconto = 0;
	@Valid
	@Size(min = 1)
	@NotNull
	private List<ItemPedidoIModel> itens = new ArrayList<>();
	@NotNull
	@Valid
	private UsuarioIdIModel cadastradoPor;
	@Valid
	private ClienteIModel cliente;
}
