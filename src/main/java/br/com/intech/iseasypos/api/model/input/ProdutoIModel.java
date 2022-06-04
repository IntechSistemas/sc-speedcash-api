package br.com.intech.iseasypos.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoIModel {
	@NotBlank
	@Size(min = 5, max = 100, message = "O campo {0} deve conter entre 5 e 100 carácteres!")
	private String descricao;
	@NotBlank
	@Size(min = 7, max = 14, message = "O campo {0} deve conter entre 7 e 14 carácteres!")
	private String cean;
	@NotBlank
	@Size(min = 8, max = 8, message = "O campo {0} deve conter exatamente 8 carácteres!")
	private String ncm;
	@NotBlank
	@Size(min = 7, max = 7, message = "O campo {0} deve conter exatamente 7 carácteres!")
	private String cest;
	@NotNull
	@PositiveOrZero
	private Integer quantidade = 0;
	@NotNull
	@PositiveOrZero
	private Integer valorCompra;
	@NotNull
	@PositiveOrZero
	private Integer valorVenda;
	@NotNull
	@Max(100)
	@Min(0)
	private Integer margemLucro;
	@NotNull
	private Boolean servico;
	@NotNull
	private Boolean ativo;
	
	@Valid
	@NotNull
	private FornecedorIdIModel fornecedor;
	@Valid
	@NotNull
	private GrupoIdIModel grupo;
	@Valid
	@NotNull
	private UnidadeIdIModel unidade;
}