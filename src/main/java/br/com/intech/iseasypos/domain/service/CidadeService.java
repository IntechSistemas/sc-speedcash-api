package br.com.intech.iseasypos.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.exception.CidadeNaoEncontradaException;
import br.com.intech.iseasypos.domain.exception.EntidadeEmUsoException;
import br.com.intech.iseasypos.domain.model.Cidade;
import br.com.intech.iseasypos.domain.model.Estado;
import br.com.intech.iseasypos.domain.repository.CidadeRepository;

@Service
public class CidadeService {
	private static final String MSG_CIDADE_EM_USO = "Cidade de codigo %d não pode ser removido pois está em uso!";
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoService estadoService;
	
	public Cidade findById(Integer cidadeId) {
		return cidadeRepository.findById(cidadeId).orElseThrow(
			() -> new CidadeNaoEncontradaException(cidadeId)
		);
	}
	@Transactional
	public Cidade save(Cidade cidade) {
		Estado estado = estadoService.findById(cidade.getEstado().getId());
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}
	@Transactional
	public void delete(Integer cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
			cidadeRepository.flush();
		}catch(EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
	}
}
