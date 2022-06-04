package br.com.intech.iseasypos.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.exception.FornecedorNaoEncontradoException;
import br.com.intech.iseasypos.domain.model.Cidade;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Fornecedor;
import br.com.intech.iseasypos.domain.repository.FornecedorRepository;

@Service
public class FornecedorService {
	@Autowired
	private FornecedorRepository fornecedorRepository;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private CidadeService cidadeService;
	
	public Page<Fornecedor> findAll(Integer empresaId, Pageable pageable){
		Empresa empresa = empresaService.findById(empresaId);
		return fornecedorRepository.findByEmpresa(empresa, pageable);
	}
	
	public Fornecedor findById(Integer fornecedorId, Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		return fornecedorRepository.findByIdAndEmpresa(fornecedorId, empresa).orElseThrow(
				() -> new FornecedorNaoEncontradoException(fornecedorId)
		);
	}
	@Transactional
	public Fornecedor save(Fornecedor fornecedor, Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		Cidade cidade = cidadeService.findById(fornecedor.getEndereco().getCidade().getId());
		fornecedor.setEmpresa(empresa);
		fornecedor.getEndereco().setCidade(cidade);
		return fornecedorRepository.save(fornecedor);
	}
	@Transactional
	public void ativar(Integer fornecedorId, Integer empresaId) {
		Fornecedor grupo = findById(fornecedorId, empresaId);
		grupo.ativar();
	}
	
	@Transactional
	public void inativar(Integer fornecedorId, Integer empresaId) {
		Fornecedor grupo = findById(fornecedorId, empresaId);
		grupo.inativar();
	}
}
