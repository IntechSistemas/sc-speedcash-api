package br.com.intech.iseasypos.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.intech.iseasypos.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "cidade")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cidade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@NotNull(groups = Groups.CidadeId.class)
	private Integer id;
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "codigo")
	private String codigo;
	@ManyToOne
	@JoinColumn(name = "estado_id")
	private Estado estado;
	
	@CreationTimestamp
	@Column(name = "data_cadastro")
	private OffsetDateTime dataCadastro;
	@UpdateTimestamp
	@Column(name = "data_atualizacao")
	private OffsetDateTime dataAtualizacao;
	
}
