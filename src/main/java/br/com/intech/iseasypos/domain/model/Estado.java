package br.com.intech.iseasypos.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.intech.iseasypos.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "estado")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@NotNull(groups = Groups.EstadoId.class)
	private Integer id;
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "sigla")
	private String sigla;
	@Column(name = "codigo")
	private String codigo;
	@CreationTimestamp
	private OffsetDateTime dataCadastro;
	@UpdateTimestamp
	private OffsetDateTime dataAtualizacao;
}
