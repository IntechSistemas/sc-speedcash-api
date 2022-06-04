package br.com.intech.iseasypos.api.model;

import br.com.intech.iseasypos.domain.model.Pix;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoModel {
	private Integer id;
	private String status;
	private Integer valorPago;
	private FormaPagamentoModel formaPagamento;
	//TODO: VERIFICARR PIX
	private Pix pix;
}
