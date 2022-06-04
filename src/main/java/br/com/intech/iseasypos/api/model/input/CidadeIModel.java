package br.com.intech.iseasypos.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeIModel {
	@NotBlank
	@Size(min = 5, max = 50, message = "O campo {0} deve conter entre 5 e 50 carácteres!")
	private String descricao;
	@NotBlank
	@Size(min = 8, max = 8, message = "O campo {0} deve conter exatamente 8 carácteres!")
	private String codigo;
	@Valid
	@NotNull
	private EstadoIdIModel estado;
}
