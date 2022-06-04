package br.com.intech.iseasypos.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.exception.CargoNaoEncontradoException;
import br.com.intech.iseasypos.domain.exception.EntidadeEmUsoException;
import br.com.intech.iseasypos.domain.model.Cargo;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Permissao;
import br.com.intech.iseasypos.domain.repository.CargoRepository;

@Service
public class CargoService {
	private static final String MSG_CARGO_EM_USO = "Cargo de código %d não pode ser removido pois está em uso!";
	
	@Autowired
	private CargoRepository cargoRepository;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private PermissaoService permissaoService;
	
	public List<Cargo> findAll(Integer empresaId){
		Empresa empresa = empresaService.findById(empresaId);
		return cargoRepository.findByEmpresa(empresa);
	}
	
	public Cargo findById(Integer cargoId, Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		return cargoRepository.findByIdAndEmpresa(cargoId,empresa).orElseThrow(
				() -> new CargoNaoEncontradoException(cargoId)		
			);
	}
	@Transactional
	public Cargo save(Cargo cargo, Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		cargo.setEmpresa(empresa);
		
		List<Permissao> permissoes = new ArrayList<Permissao>();
				
		cargo.getPermissoes().forEach(permissao -> {
			permissoes.add(permissaoService.findById(permissao.getId()));
		});
		cargo.setPermissoes(permissoes);
		return cargoRepository.save(cargo);
	}
	@Transactional
	public void delete(Integer cargoId, Integer empresaId) {
		try {
			cargoRepository.deleteByIdAndEmpresa(this.findById(cargoId, empresaId).getId(), empresaId);
			cargoRepository.flush();
		}catch(EmptyResultDataAccessException e) {
			throw new CargoNaoEncontradoException(cargoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CARGO_EM_USO, cargoId));
		}
	}
}
