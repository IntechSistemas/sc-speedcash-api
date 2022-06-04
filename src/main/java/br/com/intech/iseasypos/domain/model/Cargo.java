package br.com.intech.iseasypos.domain.model;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "cargo")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cargo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	@Column(name = "descricao")
	private String descricao;
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	@ManyToMany
	@JoinTable(name = "cargo_permissao", joinColumns = @JoinColumn(name = "cargo_id")
		, inverseJoinColumns = @JoinColumn(name = "permissao_id"))
	private List<Permissao> permissoes;
	
	
	@Column(name = "data_cadastro")
	@CreationTimestamp
	private OffsetDateTime dataCadastro;
	@Column(name = "data_atualizacao")
	@UpdateTimestamp
	private OffsetDateTime dataAtualizacao;
}
