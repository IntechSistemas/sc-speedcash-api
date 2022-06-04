package br.com.intech.iseasypos.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoIModel {
	@NotBlank
	@Size(min = 5, max = 50, message = "O campo {0} deve conter entre 5 e 50 carácteres!")
	private String descricao;
}
