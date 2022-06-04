package br.com.intech.iseasypos.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import br.com.intech.iseasypos.domain.model.Cartao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoIModel {
	@NotNull
	@PositiveOrZero
	private Integer valorPago;
	@NotNull
	@PositiveOrZero
	private Integer parcelas;
	@NotNull
	private FormaPagamentoIdIModel formaPagamento;
	@Valid
	private CartaoIModel cartao;
}
