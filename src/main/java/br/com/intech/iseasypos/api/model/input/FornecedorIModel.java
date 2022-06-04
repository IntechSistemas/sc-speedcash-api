package br.com.intech.iseasypos.api.model.input;

import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.intech.iseasypos.core.validation.CpfOrCnpj;
import br.com.intech.iseasypos.core.validation.Groups;
import br.com.intech.iseasypos.domain.model.Endereco;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class FornecedorIModel {
	@NotNull(groups = Groups.FornecedorId.class)
	private Integer id;
	@NotBlank
	@Size(min = 5, max = 100, message = "O campo {0} deve conter entre 5 e 100 carácteres!")
	private String nome;
	@NotBlank
	@CpfOrCnpj
	private String cnpj;
	private String inscricaoEstadual;
	private String inscricaoEstadualST;
	@Pattern(regexp = "\\(\\d{2,}\\)\\d{5,}\\-\\d{4}", message = "teste")
	private String fone;
	@Valid
	@NotNull
	@Embedded
	private Endereco endereco;
}
