package br.com.intech.iseasypos.api.model.input;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.intech.iseasypos.core.validation.CpfOrCnpj;
import br.com.intech.iseasypos.domain.model.RegimeTributario;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmpresaIModel {

	@NotBlank
	@Size(min = 5, max = 60, message = "O campo {0} deve conter entre 5 e 60 carácteres!")
	private String razaoSocial;
	@Size(max = 60, message = "O campo {0} deve conter no máximo 60 carácteres!")
	private String nomeFantasia;
	@NotBlank
	@CpfOrCnpj
	private String cnpj;
	@Size(max = 14, message = "O campo {0} deve conter no máximo 14 carácteres!")
	private String inscricaoEstadual;
	@NotNull
	private Boolean ativo;
	@NotNull
	private Boolean emitirNfe;
	@NotNull
	private Boolean emitirNfce;
	@NotNull
	private Boolean usaStoneApi;
	@Enumerated(EnumType.STRING)
	private RegimeTributario regimeTributario;
	@Valid
	@NotNull
	private EnderecoIModel endereco;
	@Valid
	@NotNull
	private RepresentanteLegalIModel representanteLegal;
}
