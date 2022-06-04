package br.com.intech.iseasypos.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.exception.EmpresaNaoEncontradaException;
import br.com.intech.iseasypos.domain.exception.EntidadeEmUsoException;
import br.com.intech.iseasypos.domain.model.Cidade;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.FormaPagamento;
import br.com.intech.iseasypos.domain.repository.EmpresaRepository;

@Service
public class EmpresaService {
	private static final String MSG_EMPRESA_EM_USO = "Empresa de codigo %d não pode ser removida pois está em uso!";
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private CidadeService cidadeService;
	
	public List<FormaPagamento> findByFormaPagamento(Integer empresaId){
		Empresa empresa = findById(empresaId);
		return empresa.getFormasPagamentos();
	}

	
	public Empresa findById(Integer empresaId) {
		return empresaRepository.findById(empresaId).orElseThrow(
				() -> new EmpresaNaoEncontradaException(empresaId)
		);
	}
	@Transactional
	public Empresa save(Empresa empresa) {
		Cidade cidade = cidadeService.findById(empresa.getEndereco().getCidade().getId());
		empresa.getEndereco().setCidade(cidade);
		return empresaRepository.save(empresa);
	}
	@Transactional
	public void delete(Integer empresaId) {	
		try {
			empresaRepository.deleteById(empresaId);
			empresaRepository.flush();
		}catch(EmptyResultDataAccessException e) {
			throw new EmpresaNaoEncontradaException(empresaId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_EMPRESA_EM_USO, empresaId));
		}
	}
	@Transactional
	public void ativar(Integer empresaId) {
		Empresa empresaAtual = this.findById(empresaId);
		empresaAtual.ativar();
	}
	@Transactional
	public void inativar(Integer empresaId) {
		Empresa empresaAtual = this.findById(empresaId);
		empresaAtual.inativar();
	}
	

}
