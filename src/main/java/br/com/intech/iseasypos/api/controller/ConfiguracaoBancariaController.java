package br.com.intech.iseasypos.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.intech.iseasypos.api.assembler.CargoIModelDisassembler;
import br.com.intech.iseasypos.api.assembler.CargoModelAssembler;
import br.com.intech.iseasypos.api.assembler.ConfiguracaoBancariaModelAssembler;
import br.com.intech.iseasypos.api.model.ConfiguracaoBancariaModel;
import br.com.intech.iseasypos.domain.exception.EmpresaNaoEncontradaException;
import br.com.intech.iseasypos.domain.exception.NegocioException;
import br.com.intech.iseasypos.domain.model.ConfiguracaoBancaria;
import br.com.intech.iseasypos.domain.service.ConfiguracaoBancariaService;

@RestController
@RequestMapping("/empresa/{empresaId}/configuracoes-bancarias")
public class ConfiguracaoBancariaController {
	@Autowired
	private ConfiguracaoBancariaService configuracaoBancariaService;
	@Autowired
	private ConfiguracaoBancariaModelAssembler configAssembler;
	@Autowired
	private CargoIModelDisassembler cargoDisassembler;

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public List<ConfiguracaoBancaria> findAll(@PathVariable Integer empresaId){
		return configuracaoBancariaService.findAll(empresaId); 
	}
	
	@GetMapping("/{configuracaoBancariaId}")
	public ConfiguracaoBancaria findById(@PathVariable Integer empresaId, @PathVariable Integer configuracaoBancariaId) {
		return configuracaoBancariaService.findById(configuracaoBancariaId, empresaId);
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ConfiguracaoBancariaModel save(@PathVariable Integer empresaId, @Valid @RequestBody ConfiguracaoBancaria configuracaoBancaria){
		try {
			ConfiguracaoBancaria config = configuracaoBancariaService.save(configuracaoBancaria, empresaId);
			return configAssembler.toModel(config);

		}catch (EmpresaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{configuracaoBancariaId}")
	public ResponseEntity<?> update(@PathVariable Integer empresaId, @PathVariable Integer configuracaoBancariaId, @Valid @RequestBody ConfiguracaoBancaria configuracaoBancaria){
		try {
			ConfiguracaoBancaria configAtual = configuracaoBancariaService.findById(configuracaoBancariaId, empresaId);
			BeanUtils.copyProperties(configuracaoBancaria, configAtual, "id", "dataCadastro", "empresa");
			configAtual = configuracaoBancariaService.save(configAtual, empresaId);
			return ResponseEntity.ok(configAtual);
		} catch(EmpresaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{configuracaoBancariaId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer empresaId, @PathVariable Integer configuracaoBancariaId) {
		configuracaoBancariaService.delete(configuracaoBancariaId, empresaId);
	}
}
