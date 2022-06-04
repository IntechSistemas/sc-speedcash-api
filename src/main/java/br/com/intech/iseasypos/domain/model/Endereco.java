package br.com.intech.iseasypos.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import br.com.intech.iseasypos.core.validation.Groups;
import lombok.Data;

@Embeddable
@Data
public class Endereco {
	@NotBlank
	@Size(min = 5, max = 50, message = "O campo {0} deve conter entre 5 e 50 carácteres!")
	@Column(name = "endereco_logradouro")
	private String logradouro;
	@NotBlank
	@Size(min = 1, max = 10, message = "O campo {0} deve conter entre 1 e 10 carácteres!")
	@Column(name = "endereco_numero")
	private String numero;
	@Size(max = 50, message = "O campo {0} deve conter no máximo 50 carácteres!")
	@Column(name = "endereco_complemento")
	private String complemento;
	@NotBlank
	@Size(min = 5, max = 20, message = "O campo {0} deve conter entre 5 e 20 carácteres!")
	@Column(name = "endereco_bairro")
	private String bairro;
	@NotBlank
	@Size(min = 10, max = 10, message = "O campo {0} deve conter o formato xx.xxx-xxx!")
	@Column(name = "endereco_cep")
	private String cep;
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CidadeId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "endereco_cidade_id")
	private Cidade cidade;
}
