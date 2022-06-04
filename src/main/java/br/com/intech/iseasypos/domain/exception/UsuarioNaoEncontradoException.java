package br.com.intech.iseasypos.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public UsuarioNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public UsuarioNaoEncontradoException(Integer usuarioId) {
		this(String.format("Não existe nenhum Usuário com o código %d!", usuarioId));
	}
	public UsuarioNaoEncontradoException() {
		this(String.format("Login e/ou Senha errados. Corrija e tente novamente!!!"));
	}
	
}
