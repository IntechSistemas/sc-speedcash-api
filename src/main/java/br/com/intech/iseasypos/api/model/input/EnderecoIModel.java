package br.com.intech.iseasypos.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoIModel {
	@NotBlank
	@Size(min = 5, max = 50, message = "O campo {0} deve conter entre 5 e 50 carácteres!")
	private String logradouro;
	@NotBlank
	@Size(min = 1, max = 10, message = "O campo {0} deve conter entre 1 e 10 carácteres!")
	private String numero;
	@Size(max = 50, message = "O campo {0} deve conter no máximo 50 carácteres!")
	private String complemento;
	@NotBlank
	@Size(min = 5, max = 20, message = "O campo {0} deve conter entre 5 e 20 carácteres!")
	private String bairro;
	@NotBlank
	@Size(min = 10, max = 10, message = "O campo {0} deve conter o formato xx.xxx-xxx!")
	private String cep;
	@Valid
	@NotNull
	private CidadeIdIModel cidade;
}
