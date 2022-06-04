package br.com.intech.iseasypos.domain.model.dto;

import br.com.intech.iseasypos.domain.model.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TotalVendasMes {
	private StatusPedido status;
	private Long valorTotal;
}
