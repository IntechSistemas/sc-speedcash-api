package br.com.intech.iseasypos.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public EstadoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public EstadoNaoEncontradoException(Integer estadoId) {
		this(String.format("Não existe nenhum Estado com código %d!", estadoId));
	}
}
