package br.com.intech.iseasypos.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioComSenhaIModel extends UsuarioIModel{
	@NotBlank
	@Size(min = 5, max = 100, message = "O campo {0} deve conter entre 5 e 100 carácteres!")
	private String senha;
}
