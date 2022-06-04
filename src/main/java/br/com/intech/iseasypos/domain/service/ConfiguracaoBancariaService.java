package br.com.intech.iseasypos.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.exception.ConfiguracaoBancariaNaoEncontradoException;
import br.com.intech.iseasypos.domain.exception.EntidadeEmUsoException;
import br.com.intech.iseasypos.domain.model.ConfiguracaoBancaria;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.repository.ConfiguracaoBancariaRepository;
import br.com.intech.iseasypos.infrastructure.repository.StoneBankRepository;

@Service
public class ConfiguracaoBancariaService {
	private static final String MSG_CONFIG_BANCARIRA_EM_USO = "Configuração Bancárira de código %d não pode ser removido pois está em uso!";
	@Autowired
	private ConfiguracaoBancariaRepository configuracaoBancariaRepository;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private StoneBankRepository bancoRepository;
	
	public List<ConfiguracaoBancaria> findAll(Integer empresaId){
		Empresa empresa = empresaService.findById(empresaId);
		return configuracaoBancariaRepository.findByEmpresa(empresa);
	}
	
	public ConfiguracaoBancaria findById(Integer configBancariaId, Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		return configuracaoBancariaRepository.findByIdAndEmpresa(configBancariaId, empresa)
			.orElseThrow(() -> new ConfiguracaoBancariaNaoEncontradoException(configBancariaId));
	}
	@Transactional
	public ConfiguracaoBancaria save(ConfiguracaoBancaria configuracaoBancaria, Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		configuracaoBancaria.setEmpresa(empresa);
		if(configuracaoBancaria.getStonecode() != null && !configuracaoBancaria.getStonecode().isEmpty() && (configuracaoBancaria.getClientId() == null ||configuracaoBancaria.getClientId().isEmpty())) {
			configuracaoBancaria.setClientId(bancoRepository.cadastrarEstabelecimento(empresa, configuracaoBancaria.getStonecode()));
		}
		return configuracaoBancariaRepository.save(configuracaoBancaria);
	}
	
	@Transactional
	public void delete(Integer configuracaoBancariaId, Integer empresaId) {
		try {
			configuracaoBancariaRepository.deleteByIdAndEmpresa(this.findById(configuracaoBancariaId, empresaId).getId(), empresaId);
			configuracaoBancariaRepository.flush();
		}catch(EmptyResultDataAccessException e) {
			throw new ConfiguracaoBancariaNaoEncontradoException(configuracaoBancariaId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CONFIG_BANCARIRA_EM_USO, configuracaoBancariaId));
		}
	}
}
