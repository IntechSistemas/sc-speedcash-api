package br.com.intech.iseasypos.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.intech.iseasypos.core.validation.CpfOrCnpj;
import br.com.intech.iseasypos.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "cliente")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente {
	@NotNull(groups = Groups.ClienteId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	@NotNull
	@Size(min = 5, max = 100, message = "O campo {0} deve conter entre 5 e 100 carácteres")
	@Column(name = "nome")
	private String nome;
	@CpfOrCnpj
	@Column(name = "cnpj")
	private String cnpj;
	@Size(max = 14, message = "O campo {0} deve conter no máximo 14 carácteres")
	@Column(name = "inscricao_estadual")
	private String inscricaoEstadual;
	@Size(max = 14, message = "O campo {0} deve conter no máximo 14 carácteres")
	@Column(name = "inscricao_municipal")
	private String inscricaoMunicipal;
	@NotNull
	@Size(max = 14, message = "O campo {0} deve conter no máximo 14 carácteres! (Formato: (xx)xxxxx-xxxx)")
	@Column(name = "fone")
	private String fone;
	@NotNull
	@Email
	@Column(name = "email")
	private String email;
	@Embedded
	@NotNull
	@Valid
	private Endereco endereco;
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.EmpresaId.class)
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
}
