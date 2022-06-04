package br.com.intech.iseasypos.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class EstadoIModel {
	@NotBlank
	@Size(min = 4, max = 50, message = "O campo {0} deve conter de 4 a 50 caracteres!")
	private String descricao;
	@NotBlank
	@Size(min = 2, max = 2, message = "O campo {0} deve conter exatamente 2 carácteres!")
	private String sigla;
	@NotBlank
	@Size( min = 2, max = 2, message = "O campo {0} deve conter exatamente 2 carácteres!")
	private String codigo;
}
