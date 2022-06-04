package br.com.intech.iseasypos.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public PermissaoNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public PermissaoNaoEncontradaException(Integer permissaoId) {
		this(String.format("Não existe nenhuma Permissao com o código %d!", permissaoId));
	}
}
