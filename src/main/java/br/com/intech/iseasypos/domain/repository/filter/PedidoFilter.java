package br.com.intech.iseasypos.domain.repository.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoFilter {
	private String status;
	private String cadastradoPor;
	private String confirmadoPor;
	private String canceladoPor;
	private String cliente;
	@JsonIgnore
	private Integer empresa;
}
