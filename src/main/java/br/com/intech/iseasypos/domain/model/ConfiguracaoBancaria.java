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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.intech.iseasypos.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "configuracao_bancaria")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ConfiguracaoBancaria {
	@NotNull(groups = Groups.FornecedorId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	@NotBlank
	@Size(min = 5, max = 20, message = "O campo {0} deve conter entre 5 e 20 carácteres!")
	@Column(name = "descricao")
	private String descricao;
	@Size(max = 100, message = "O campo {0} deve conter no máximo 100 carácteres!")
	@Column(name = "token")
	private String token;
	@Size(max = 100, message = "O campo {0} deve conter no máximo 100 carácteres!")
	@Column(name = "secret")
	private String secret;
	@Size(max = 100, message = "O campo {0} deve conter no máximo 100 carácteres!")
	@Column(name = "client_id")
	private String clientId;
	@Size(max = 100, message = "O campo {0} deve conter no máximo 100 carácteres!")
	@Column(name = "oauth_key")
	private String oauthKey;
	@Size(max = 100, message = "O campo {0} deve conter no máximo 100 carácteres!")
	@Column(name = "url_api")
	private String urlApi;
	@Size(max = 100, message = "O campo {0} deve conter no máximo 100 carácteres!")
	@Column(name = "loja_id")
	private String lojaId;
	@Size(max = 100, message = "O campo {0} deve conter no máximo 100 carácteres!")
	@Column(name = "caixa_id")
	private String caixaId;
	@Size(max = 100, message = "O campo {0} deve conter no máximo 100 carácteres!")
	@Column(name = "qrcode")
	private String qrCode;
	@Size(max = 100, message = "O campo {0} deve conter no máximo 100 carácteres!")
	@Column(name = "seller_token")
	private String sellerToken;
	@Size(max = 100, message = "O campo {0} deve conter no máximo 100 carácteres!")
	@Column(name = "stone_code")
	private String stonecode;
	@Size(max = 6, message = "O campo {0} deve conter no máximo 6 carácteres!")
	@Column(name = "codigo_convite")
	private String codigoConvite;
	@Size(max = 4, message = "O campo {0} deve conter no máximo 4 carácteres!")
	@Column(name = "agencia_numero")
	private String agenciaNumero;
	@Size(max = 20, message = "O campo {0} deve conter no máximo 20 carácteres!")
	@Column(name = "agencia_conta_pj")
	private String agenciaContaPJ;
	@Size(max = 50, message = "O campo {0} deve conter no máximo 50 carácteres!")
	@Column(name = "hash")
	private String hash;
	@Size(max = 1, message = "O campo {0} deve conter apenas 1 carácteres!")
	@Column(name = "tipo_convenio")
	private String tipoConvenio;
	
	@NotNull
	@Column(name = "ambiente_producao")
	private Boolean ambienteProducao;
	@NotNull
	@Column(name = "ativo")
	private Boolean ativo;
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.EmpresaId.class)
	@NotNull
	@JsonIgnoreProperties("configsBancarias")
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
