package br.com.intech.iseasypos.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.intech.iseasypos.domain.exception.PermissaoNaoEncontradaException;
import br.com.intech.iseasypos.domain.model.Permissao;
import br.com.intech.iseasypos.domain.repository.PermissaoRepository;

@Service
public class PermissaoService {
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public Permissao findById(Integer permissaoId) {
		return permissaoRepository.findById(permissaoId).orElseThrow(
			() -> new PermissaoNaoEncontradaException(permissaoId)
		);
	}
}
