package br.com.intech.iseasypos.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoIModel {
	@NotBlank
	@Size(min = 5, max = 20, message = "O campo {0} deve conter entre 5 e 20 carácteres!")
	private String descricao;
}
