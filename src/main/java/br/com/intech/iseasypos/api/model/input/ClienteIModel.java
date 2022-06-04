package br.com.intech.iseasypos.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.intech.iseasypos.core.validation.CpfOrCnpj;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClienteIModel {
	private Integer id;
	@NotNull
	@Size(min = 5, max = 100, message = "O campo {0} deve conter entre 5 e 100 carácteres")
	private String nome;
	@CpfOrCnpj
	private String cnpj;
	@Size(max = 14, message = "O campo {0} deve conter no máximo 14 carácteres")
	private String inscricaoEstadual;
	@Size(max = 14, message = "O campo {0} deve conter no máximo 14 carácteres")
	private String inscricaoMunicipal;
	@Size(max = 14, message = "O campo {0} deve conter no máximo 14 carácteres! (Formato: (xx)xxxxx-xxxx)")
	private String fone;
	@Email
	private String email;
	@Valid
	private EnderecoIModel endereco;
	@Valid
	@NotNull
	private EmpresaIdIModel empresa;
}
