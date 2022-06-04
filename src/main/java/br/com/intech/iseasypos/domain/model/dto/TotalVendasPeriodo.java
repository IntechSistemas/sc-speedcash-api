package br.com.intech.iseasypos.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TotalVendasPeriodo {
	private Integer vendasDia = 0;
	private Integer vendasMes = 0;
	private Integer vendasAno = 0;
	private Integer totalProdutos = 0;
	private Integer totalEstoque = 0;
}
