package br.com.intech.iseasypos.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "cartao")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cartao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	@Column(name = "autorizacao")
	private String autorizacao;
	@Column(name = "cnpj")
	private String cnpj;
	@Column(name = "pos")
	private String pos;
	@Column(name = "nsu")
	private String nsu;
	@Column(name = "bandeira")
	@Enumerated(EnumType.STRING)
	private Bandeira bandeira;
	
}
