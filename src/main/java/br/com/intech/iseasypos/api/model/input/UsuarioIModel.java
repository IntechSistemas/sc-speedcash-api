package br.com.intech.iseasypos.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioIModel {
	@NotBlank
	@Size(min = 5, max = 100, message = "O campo {0} deve conter entre 5 e 100 carácteres!")
	private String nome;
	@NotBlank
	@Size(min = 5, max = 20, message = "O campo {0} deve conter entre 5 e 20 carácteres!")
	private String login;	
	@NotBlank
	@Size(max = 100, message = "O campo {0} deve conter no máximo 100 carácteres!")
	private String email;
	@NotNull
	private Boolean ativo;
	@Valid
	@NotNull
	private EmpresaIdIModel empresa;
	@NotNull
	@Valid
	private List<CargoIdIModel> cargos;
}