package br.com.intech.iseasypos.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.exception.EntidadeEmUsoException;
import br.com.intech.iseasypos.domain.exception.EstadoNaoEncontradoException;
import br.com.intech.iseasypos.domain.model.Estado;
import br.com.intech.iseasypos.domain.repository.EstadoRepository;

@Service
public class EstadoService {
	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido pois está em uso!";
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado findById(Integer estadoId) {
		return estadoRepository.findById(estadoId).orElseThrow(
			() -> new EstadoNaoEncontradoException(estadoId)
		);
	}
	@Transactional
	public Estado save(Estado estado) {
		return estadoRepository.save(estado);
	}
	@Transactional
	public void delete(Integer estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
			estadoRepository.flush();
		}catch(EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException(estadoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, estadoId));
		}
	}
}
