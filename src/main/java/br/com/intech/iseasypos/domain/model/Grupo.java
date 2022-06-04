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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.intech.iseasypos.core.validation.Groups;
import br.com.intech.iseasypos.core.validation.Groups.EmpresaId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "grupo")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Grupo {
	@NotNull(groups = Groups.GrupoId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	@NotBlank
	@Size(min = 5, max = 50, message = "O campo {0} deve conter entre 5 e 50 carácteres!")
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "ativo")
	private Boolean ativo = Boolean.TRUE;
	
	@Valid
	@ConvertGroup(from = Default.class, to = EmpresaId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	
	@JsonIgnore
	@Column(name = "data_cadastro")
	@CreationTimestamp
	private OffsetDateTime dataCadastro;
	@JsonIgnore
	@Column(name = "data_atualizacao")
	@UpdateTimestamp
	private OffsetDateTime dataAtualizacao;
	
	public void ativar() {
		setAtivo(true);
	}
	
	public void inativar() {
		setAtivo(false);
	}
}
