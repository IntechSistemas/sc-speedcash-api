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
public class CargoIModel {
	@NotBlank
	@Size(min = 5, max = 30, message = "O campo {0} deve conter entre 5 e 30 carácteres!")
	private String descricao;
	@Valid
	@NotNull
	private EmpresaIdIModel empresa;
	@NotNull
	private List<PermissaoIdIModel> permissoes;
}
