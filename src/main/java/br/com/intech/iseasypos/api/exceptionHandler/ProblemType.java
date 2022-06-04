package br.com.intech.iseasypos.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação da regra de negócio"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	MENSAGEM_NAO_LEGIVEL("/mensagem-incompreensivel", "Mensagem Incompreensível"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title){
		this.uri = "http://localhost:8080/" + path;
		this.title = title;
	}
}
