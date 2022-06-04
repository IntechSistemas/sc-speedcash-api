package br.com.intech.iseasypos.domain.exception;

public class FornecedorNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public FornecedorNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public FornecedorNaoEncontradoException(Integer fornecedorId) {
		this(String.format("Não existe nenhum Fornecedor com o código %d!", fornecedorId));
	}
}
