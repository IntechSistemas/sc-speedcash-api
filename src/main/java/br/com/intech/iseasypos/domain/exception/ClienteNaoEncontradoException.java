package br.com.intech.iseasypos.domain.exception;

public class ClienteNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public ClienteNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public ClienteNaoEncontradoException(Integer clienteId) {
		this(String.format("Não existe nenhum Cliente com o código %d!", clienteId));
	}
}
