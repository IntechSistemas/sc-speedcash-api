package br.com.intech.iseasypos.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public CidadeNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public CidadeNaoEncontradaException(Integer cidadeId) {
		this(String.format("Não existe nenhuma Cidade com o código %d!", cidadeId));
	}
}
