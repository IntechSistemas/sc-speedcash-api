package br.com.intech.iseasypos.domain.exception;

public class EmpresaNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public EmpresaNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public EmpresaNaoEncontradaException(Integer empresaId) {
		this(String.format("Não existe nenhuma Empresa com o código %d!", empresaId));
	}
}
