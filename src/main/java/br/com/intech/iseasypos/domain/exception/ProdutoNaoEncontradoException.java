package br.com.intech.iseasypos.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public ProdutoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public ProdutoNaoEncontradoException(Integer produtoId) {
		this(String.format("Não existe nenhum Produto com o código %d!", produtoId));
	}
}
