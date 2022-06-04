package br.com.intech.iseasypos.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.intech.iseasypos.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "produto")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Produto {
	@NotNull(groups = Groups.ProdutoId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	@NotBlank
	@Size(min = 5, max = 100, message = "O campo {0} deve conter entre 5 e 100 carácteres!")
	@Column(name = "descricao")
	private String descricao;
	@NotNull
	@Column(name = "cprod")
	private Integer cProd;
	@NotBlank
	@Size(min = 7, max = 14, message = "O campo {0} deve conter entre 7 e 14 carácteres!")
	@Column(name = "cean")
	private String cean;
	@NotBlank
	@Size(min = 8, max = 8, message = "O campo {0} deve conter exatamente 8 carácteres!")
	@Column(name = "ncm")
	private String ncm;
	@NotBlank
	@Size(min = 7, max = 7, message = "O campo {0} deve conter exatamente 7 carácteres!")
	@Column(name = "cest")
	private String cest;
	@NotNull
	@Column(name = "quantidade")
	@PositiveOrZero
	private Integer quantidade;
	@NotNull
	@Column(name = "valor_compra")
	@PositiveOrZero
	private Integer valorCompra;
	@NotNull
	@Column(name = "valor_venda")
	@PositiveOrZero
	private Integer valorVenda;
	@NotNull
	@Max(100)
	@Min(0)
	@Column(name = "margem_lucro")
	private Integer margemLucro;
	@NotNull
	@Column(name = "servico")
	private Boolean servico;
	@NotNull
	@Column(name = "ativo")
	private Boolean ativo = Boolean.TRUE;
	
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.FornecedorId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "fornecedor_id")
	private Fornecedor fornecedor;
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.GrupoId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "grupo_id")
	private Grupo grupo;
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.UnidadeId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "unidade_id")
	private Unidade unidade;
	
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.EmpresaId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	
	@OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<FotoProduto> catalogo = new ArrayList<>();
	
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