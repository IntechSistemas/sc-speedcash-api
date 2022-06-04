package br.com.intech.iseasypos.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import br.com.intech.iseasypos.api.assembler.EmpresaIModelDisassembler;
import br.com.intech.iseasypos.api.assembler.EmpresaModelAssembler;
import br.com.intech.iseasypos.api.assembler.FormaPagamentoModelAssembler;
import br.com.intech.iseasypos.api.model.EmpresaModel;
import br.com.intech.iseasypos.api.model.FormaPagamentoModel;
import br.com.intech.iseasypos.api.model.input.EmpresaIModel;
import br.com.intech.iseasypos.domain.exception.CidadeNaoEncontradaException;
import br.com.intech.iseasypos.domain.exception.NegocioException;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.repository.EmpresaRepository;
import br.com.intech.iseasypos.domain.service.EmpresaService;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private EmpresaModelAssembler empresaAssembler;
	@Autowired
	private EmpresaIModelDisassembler empresaDisassembler;
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoAssembler;

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public List<EmpresaModel> findAll(){
		return empresaAssembler.toCollectionModel(empresaRepository.findAll());	
	}
	
	@GetMapping("/{empresaId}/formas-pagamento")
	public List<FormaPagamentoModel> findByFormaPagagmentos(@PathVariable Integer empresaId){
		return formaPagamentoAssembler.toCollectionModel(empresaService.findByFormaPagamento(empresaId));
	}
	
	@GetMapping("/{empresaId}")
	public EmpresaModel findById(@PathVariable Integer empresaId) {
		return empresaAssembler.toModel(empresaService.findById(empresaId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EmpresaModel save(@Valid @RequestBody EmpresaIModel empresaIModel){
		try {
			Empresa empresa = empresaDisassembler.toDomainObject(empresaIModel);
			return empresaAssembler.toModel(empresaService.save(empresa));
		}catch (CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{empresaId}")
	public ResponseEntity<?> update(@PathVariable Integer empresaId,@Valid @RequestBody EmpresaIModel empresaIModel){
		try {
			Empresa empresaAtual = empresaService.findById(empresaId);
			empresaDisassembler.copyToDomainObject(empresaIModel, empresaAtual);
			return ResponseEntity.ok(empresaAssembler.toModel(empresaService.save(empresaAtual)));
		} catch(CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{empresaId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer empresaId) {
		empresaService.delete(empresaId);			
	}
	
	@PutMapping("/{empresaId}/ativo")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Integer empresaId) {
		empresaService.ativar(empresaId);
	}
	@DeleteMapping("/{empresaId}/ativo")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Integer empresaId) {
		empresaService.inativar(empresaId);
	}
	
	
}
