package br.com.intech.iseasypos.domain.exception;

public class UnidadeNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public UnidadeNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public UnidadeNaoEncontradoException(Integer unidadeId) {
		this(String.format("Não existe nenhuma Unidade com o código %d!", unidadeId));
	}
}
