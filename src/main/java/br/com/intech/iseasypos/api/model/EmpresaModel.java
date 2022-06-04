package br.com.intech.iseasypos.api.model;

import br.com.intech.iseasypos.domain.model.RegimeTributario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaModel {
	private Integer id;
	private String razaoSocial;
	private String nomeFantasia;
	private String cnpj;
	private String inscricaoEstadual;
	private Boolean ativo;
	private Boolean emitirNfe;
	private Boolean emitirNfce;
	private Boolean usaStoneApi;
	private RegimeTributario regimeTributario;
	private EnderecoModel endereco;
	private RepresentanteLegalModel representanteLegal;
}
