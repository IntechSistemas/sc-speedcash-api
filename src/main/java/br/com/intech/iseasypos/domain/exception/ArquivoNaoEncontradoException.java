package br.com.intech.iseasypos.domain.exception;

public class ArquivoNaoEncontradoException extends EntidadeNaoEncontradaException{
	 private static final long serialVersionUID = 1L;

	    public ArquivoNaoEncontradoException(String mensagem) {
	        super(mensagem);
	    }

	    public ArquivoNaoEncontradoException(Integer empresaId, Integer produtoId) {
	        this(String.format("Não existe um cadastro de foto do produto com código %d para a Empresa de código %d",
	                produtoId, empresaId));
	    }
	}