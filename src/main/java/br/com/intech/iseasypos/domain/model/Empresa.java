package br.com.intech.iseasypos.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.intech.iseasypos.core.validation.CpfOrCnpj;
import br.com.intech.iseasypos.core.validation.EmiteNFCE;
import br.com.intech.iseasypos.core.validation.EmiteNFE;
import br.com.intech.iseasypos.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "empresa")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EmiteNFE(valueField = "emitirNfe", configField = "configDocumentoFiscal", contentField="configNFE" )
@EmiteNFCE(valueField = "emitirNfce", configField = "configDocumentoFiscal", contentField="configNFCE" )
public class Empresa {
	@NotNull(groups = Groups.EmpresaId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	@NotBlank
	@Size(min = 5, max = 60, message = "O campo {0} deve conter entre 5 e 60 carácteres!")
	@Column(name = "razao_social")
	private String razaoSocial;
	@Size(max = 60, message = "O campo {0} deve conter no máximo 60 carácteres!")
	@Column(name = "nome_fantasia")
	private String nomeFantasia;
	@NotBlank
	@CpfOrCnpj
	@Column(name = "cnpj")
	private String cnpj;
	@Size(max = 14, message = "O campo {0} deve conter no máximo 14 carácteres!")
	@Column(name = "inscricao_estadual")
	private String inscricaoEstadual;
	@NotNull
	@Column(name = "ativo")
	private Boolean ativo;
	@NotNull
	@Column(name = "emitir_nfe")
	private Boolean emitirNfe;
	@NotNull
	@Column(name = "emitir_nfce")
	private Boolean emitirNfce;
	@Column(name = "usa_stone_api")
	private Boolean usaStoneApi;
	@Enumerated(EnumType.STRING)
	@Column(name = "regime_tributario")
	private RegimeTributario regimeTributario;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "configuracao_geral_id")
	private ConfiguracaoGeral configGeral;
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "configuracao_documento_fiscal_id")
	private ConfiguracaoDocumentoFiscal configDocumentoFiscal;
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "representante_legal_id")
	private RepresentanteLegal representanteLegal;
	@JsonIgnoreProperties("empresa")
	@OneToMany(mappedBy = "empresa" )
	private List<ConfiguracaoBancaria> configsBancarias;
	
	@Embedded
	@Valid
	@NotNull
	private Endereco endereco;
	
	@JsonIgnoreProperties("empresa")
	@OneToMany(mappedBy = "empresa")
	private List<Produto> produtos = new ArrayList<>(); 
	@ManyToMany
	@JoinTable(name = "forma_pagamento_empresa",
		joinColumns = @JoinColumn(name = "empresa_id"),
		inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamentos = new ArrayList<>(); 
	
	@JsonIgnore
	@Column(name = "data_cadastro")
	@CreationTimestamp
	private OffsetDateTime dataCadastro;
	@JsonIgnore
	@Column(name = "data_atualizacao")
	@UpdateTimestamp
	private OffsetDateTime dataAtualizacao;
	
	public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
	    return this.getFormasPagamentos().contains(formaPagamento);
	}

	public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
	    return !aceitaFormaPagamento(formaPagamento);
	}
	
	public void ativar() {
		this.ativo = true;
	}
	public void inativar() {
		this.ativo = false;
	}
	
}