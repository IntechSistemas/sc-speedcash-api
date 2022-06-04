package br.com.intech.iseasypos.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "configuracao_geral")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ConfiguracaoGeral {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	@Column(name = "quantidade_mesas")
	private Integer qtdMesas;
	@NotBlank
	@Column(name = "cor_tema")
	private String corTema;	
	@NotBlank
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_conexao")
	private TipoConexao tipoConexao;
	
	@JsonIgnore
	@Column(name = "data_cadastro")
	@CreationTimestamp
	private OffsetDateTime dataCadastro;
	@JsonIgnore
	@Column(name = "data_atualizacao")
	@UpdateTimestamp
	private OffsetDateTime dataAtualizacao;
}
