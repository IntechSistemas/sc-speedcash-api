package br.com.intech.iseasypos.domain.exception;

public class ConfiguracaoBancariaNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public ConfiguracaoBancariaNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public ConfiguracaoBancariaNaoEncontradoException(Integer cargoId) {
		this(String.format("Não existe nenhuma Configuração Bancária com o código %d!", cargoId));
	}
}
