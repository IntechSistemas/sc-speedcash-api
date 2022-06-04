package br.com.intech.iseasypos.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.exception.EntidadeEmUsoException;
import br.com.intech.iseasypos.domain.exception.UnidadeNaoEncontradoException;
import br.com.intech.iseasypos.domain.model.Unidade;
import br.com.intech.iseasypos.domain.repository.UnidadeRepository;

@Service
public class UnidadeService {
	@Autowired
	private UnidadeRepository unidadeRepository;
	
	public Unidade findById(Integer unidadeId) {
		return unidadeRepository.findById(unidadeId).orElseThrow(
				() -> new UnidadeNaoEncontradoException(unidadeId)
		);
	}
	@Transactional
	public Unidade save(Unidade unidade) {	
		return unidadeRepository.save(unidade);
	}
	@Transactional
	public void delete(Integer unidadeId) {
		try {
			unidadeRepository.deleteById(unidadeId);
			unidadeRepository.flush();
		}catch(EmptyResultDataAccessException e) {
			throw new UnidadeNaoEncontradoException(unidadeId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Unidade de codigo %d não pode ser removido pois está em uso!", unidadeId));
		}
	}
}
