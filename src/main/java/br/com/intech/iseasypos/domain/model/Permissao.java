package br.com.intech.iseasypos.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "permissao")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Permissao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private int id;
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "nome")
	private String nome;
}