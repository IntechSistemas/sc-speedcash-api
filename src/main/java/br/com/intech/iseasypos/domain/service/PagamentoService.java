package br.com.intech.iseasypos.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.model.Pagamento;
import br.com.intech.iseasypos.domain.repository.PagamentoRepository;

@Service
public class PagamentoService {
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Transactional
	public Pagamento save(Pagamento pagamento) {
		return pagamentoRepository.save(pagamento);
	}
}
