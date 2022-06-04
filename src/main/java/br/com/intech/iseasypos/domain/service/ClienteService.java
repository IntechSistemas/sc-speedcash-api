package br.com.intech.iseasypos.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.exception.ClienteNaoEncontradoException;
import br.com.intech.iseasypos.domain.exception.EntidadeEmUsoException;
import br.com.intech.iseasypos.domain.model.Cidade;
import br.com.intech.iseasypos.domain.model.Cliente;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.repository.ClienteRepository;

@Service
public class ClienteService {
	private static final String MSG_CLIENTE_EM_USO = "Cliente de codigo %d não pode ser removido pois está em uso!";
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private CidadeService cidadeService;
	
	public List<Cliente> findAll(Integer empresaId){
		Empresa empresa = empresaService.findById(empresaId);
		return clienteRepository.findByEmpresa(empresa);
	}
	
	public Cliente findByCnpj(String cnpj, Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		return clienteRepository.findByCnpjAndEmpresa(cnpj, empresa)
			.orElseThrow(() -> new ClienteNaoEncontradoException(cnpj));
	}
	
	public Cliente findById(Integer clienteId, Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		return clienteRepository.findByIdAndEmpresa(clienteId, empresa)
			.orElseThrow(() -> new ClienteNaoEncontradoException(clienteId));
	}
	@Transactional
	public Cliente save(Cliente cliente, Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		Cidade cidade = cidadeService.findById(cliente.getEndereco().getCidade().getId());
		cliente.setEmpresa(empresa);
		cliente.getEndereco().setCidade(cidade);
		return clienteRepository.save(cliente);
	}
	@Transactional
	public void delete(Integer clienteId, Integer empresaId) {
		try {
			clienteRepository.deleteByIdAndEmpresa(this.findById(clienteId, empresaId).getId(), empresaId);
			clienteRepository.flush();
		}catch(EmptyResultDataAccessException e) {
			throw new ClienteNaoEncontradoException(clienteId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CLIENTE_EM_USO, clienteId));
		}
	}
}
