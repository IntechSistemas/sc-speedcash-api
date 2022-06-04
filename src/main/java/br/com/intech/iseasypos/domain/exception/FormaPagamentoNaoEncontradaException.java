package br.com.intech.iseasypos.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public FormaPagamentoNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public FormaPagamentoNaoEncontradaException(Integer formaPagamentoId) {
		this(String.format("Não existe nenhuma Forma de Pagamento com o código %d!", formaPagamentoId));
	}
}
