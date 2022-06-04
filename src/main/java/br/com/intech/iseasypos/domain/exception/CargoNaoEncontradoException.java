package br.com.intech.iseasypos.domain.exception;

public class CargoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public CargoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public CargoNaoEncontradoException(Integer cargoId) {
		this(String.format("Não existe nenhum Cargo com o código %d!", cargoId));
	}
}
