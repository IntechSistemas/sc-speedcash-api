package br.com.intech.iseasypos.domain.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "pagamento")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pagamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	@Enumerated(EnumType.STRING)
	private StatusPagamento status;
	@PositiveOrZero
	private Integer valorPago;
	@PositiveOrZero
	private Integer parcelas;
	@ManyToOne
	@JoinColumn(name = "forma_pagamento_id")
	private FormaPagamento formaPagamento;
	@ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cartao_id")
	private Cartao cartao;
	@Column(name = "transacao_id")
	private String transacaoId;
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pix_id")
	private Pix pix;
}
