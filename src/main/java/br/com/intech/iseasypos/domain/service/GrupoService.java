package br.com.intech.iseasypos.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.exception.GrupoNaoEncontradoException;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Grupo;
import br.com.intech.iseasypos.domain.repository.GrupoRepository;

@Service
public class GrupoService {
	@Autowired
	private GrupoRepository grupoRepository;
	@Autowired
	private EmpresaService empresaService;
	
	public Page<Grupo> findAll(Integer empresaId, Pageable pageable){
		Empresa empresa = empresaService.findById(empresaId);
		return grupoRepository.findByEmpresa(empresa, pageable);
	}
	
	public Grupo findById(Integer grupoId, Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		return grupoRepository.findByIdAndEmpresa(grupoId, empresa)
			.orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
	}
	@Transactional
	public Grupo save(Grupo grupo, Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		grupo.setEmpresa(empresa);
		return grupoRepository.save(grupo);
	}
	@Transactional
	public void ativar(Integer grupoId, Integer empresaId) {
		Grupo grupo = findById(grupoId, empresaId);
		grupo.ativar();
	}
	
	@Transactional
	public void inativar(Integer grupoId, Integer empresaId) {
		Grupo grupo = findById(grupoId, empresaId);
		grupo.inativar();
	}
}
