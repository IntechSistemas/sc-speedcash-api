package br.com.intech.iseasypos.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public GrupoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public GrupoNaoEncontradoException(Integer grupoId) {
		this(String.format("Não existe nenhum Grupo com o código %d!", grupoId));
	}
}
