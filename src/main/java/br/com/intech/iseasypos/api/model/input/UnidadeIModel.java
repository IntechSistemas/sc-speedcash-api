package br.com.intech.iseasypos.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnidadeIModel {
	@NotBlank
	@Size(min = 2, max = 20, message = "O campo {0} deve conter entre 2 e 20 carácteres!")
	private String descricao;
	@NotBlank
	@Size(min = 2, max = 2, message = "O campo {0} deve conter exatamente 2 carácteres!")
	private String sigla;
}
