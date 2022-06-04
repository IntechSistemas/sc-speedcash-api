package br.com.intech.iseasypos.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.intech.iseasypos.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "configuracao_documento_fiscal")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ConfiguracaoDocumentoFiscal {
	@NotNull(groups = Groups.ConfigDocFiscalId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	@NotBlank
	@Column(name = "certificado_caminho")
	private String certificadoCaminho;
	@NotBlank
	@Column(name = "certificado_senha")
	private String certificadoSenha;
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "configuracao_nfe_id")
	private ConfiguracaoNFE configNFE;
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "configuracao_nfce_id")
	private ConfiguracaoNFCE configNFCE;
	
	@JsonIgnore
	@Column(name = "data_cadastro")
	@CreationTimestamp
	private OffsetDateTime dataCadastro;
	@JsonIgnore
	@Column(name = "data_atualizacao")
	@UpdateTimestamp
	private OffsetDateTime dataAtualizacao;
}
