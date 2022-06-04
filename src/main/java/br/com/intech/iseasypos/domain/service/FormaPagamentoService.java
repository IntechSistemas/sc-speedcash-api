package br.com.intech.iseasypos.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.exception.EntidadeEmUsoException;
import br.com.intech.iseasypos.domain.exception.FormaPagamentoNaoEncontradaException;
import br.com.intech.iseasypos.domain.model.FormaPagamento;
import br.com.intech.iseasypos.domain.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {
	private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de Pagamento de código %d não pode ser removido pois está em uso!";
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	public FormaPagamento findById(Integer formaPagamentoId) {
		return formaPagamentoRepository.findById(formaPagamentoId).orElseThrow(
				() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId)
		);
	}
	@Transactional
	public FormaPagamento save(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	
	@Transactional
	public void delete(Integer formaPagamentoId) {
		try {
			formaPagamentoRepository.deleteById(formaPagamentoId);
			formaPagamentoRepository.flush();
		}catch(EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(formaPagamentoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
		}
	}
}
