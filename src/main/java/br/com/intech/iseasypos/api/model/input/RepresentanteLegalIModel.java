package br.com.intech.iseasypos.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepresentanteLegalIModel {
	@NotBlank
	@Size(min = 5, max = 60, message = "O campo {0} deve conter entre 5 e 60 carácteres!")
	private String nome;
	@NotBlank
	@CPF
	private String cpf;
	@NotBlank
	@Size(max = 100, message = "O campo {0} deve conter no máximo 100 carácteres!")
	private String email;
	@NotNull
	@Size(max = 14, message = "O campo {0} deve conter no máximo 14 carácteres! (Formato: (xx)xxxxx-xxxx)")
	private String fone;
}
