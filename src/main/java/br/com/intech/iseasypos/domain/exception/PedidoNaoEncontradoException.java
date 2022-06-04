package br.com.intech.iseasypos.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public PedidoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public PedidoNaoEncontradoException(Integer pedidoId) {
		this(String.format("Não existe nenhum Pedido com o código %d!", pedidoId));
	}
}
