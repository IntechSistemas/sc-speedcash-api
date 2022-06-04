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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "configuracao_nfe")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ConfiguracaoNFE {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	@NotNull
	@Column(name = "serie")
	private Integer serie;
	@NotNull
	@Column(name = "numero")
	private Integer numero;
	@NotNull
	@Column(name = "ambiente_producao")
	private Boolean ambienteProducao;
	
	@JsonIgnore
	@Column(name = "data_cadastro")
	@CreationTimestamp
	private OffsetDateTime dataCadastro;
	@JsonIgnore
	@Column(name = "data_atualizacao")
	@UpdateTimestamp
	private OffsetDateTime dataAtualizacao;
}
