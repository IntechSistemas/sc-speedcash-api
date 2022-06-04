package br.com.intech.iseasypos.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

import br.com.intech.iseasypos.api.assembler.FornecedorIModelDisassembler;
import br.com.intech.iseasypos.api.assembler.FornecedorModelAssembler;
import br.com.intech.iseasypos.api.model.FornecedorModel;
import br.com.intech.iseasypos.api.model.input.FornecedorIModel;
import br.com.intech.iseasypos.domain.exception.EntidadeNaoEncontradaException;
import br.com.intech.iseasypos.domain.exception.NegocioException;
import br.com.intech.iseasypos.domain.model.Fornecedor;
import br.com.intech.iseasypos.domain.service.FornecedorService;

@RestController
@RequestMapping("/empresa/{empresaId}/fornecedores")
public class FornecedorController {
	@Autowired
	private FornecedorService fornecedorService;
	@Autowired
	private FornecedorModelAssembler fornecedorAssembler;
	@Autowired
	private FornecedorIModelDisassembler fornecedorDisassembler;
	
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public Page<FornecedorModel> findAll(@PathVariable Integer empresaId, Pageable pageable){
		Page<Fornecedor> fornecedoresPage = fornecedorService.findAll(empresaId, pageable); 
		List<FornecedorModel> fornecedoresModel = fornecedorAssembler.toCollectionModel(fornecedoresPage.getContent());

		return new PageImpl<>(fornecedoresModel, pageable, fornecedoresPage.getTotalElements()); 
	}
	
	@GetMapping("/{fornecedorId}")
	public FornecedorModel findById(@PathVariable Integer empresaId, @PathVariable Integer fornecedorId) {
		Fornecedor fornecedor =  fornecedorService.findById(fornecedorId, empresaId);
		return fornecedorAssembler.toModel(fornecedor);
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public FornecedorModel save(@PathVariable Integer empresaId, @Valid @RequestBody FornecedorIModel fornecedorIModel){
		Fornecedor fornecedor = fornecedorDisassembler.toDomainObject(fornecedorIModel);
		fornecedor = fornecedorService.save(fornecedor, empresaId);
		return fornecedorAssembler.toModel(fornecedor);
	}
	
	@PutMapping("/{fornecedorId}")
	public ResponseEntity<?> update(@PathVariable Integer empresaId, @PathVariable Integer fornecedorId, @Valid @RequestBody FornecedorIModel fornecedorIModel){
		try {
			Fornecedor fornecedorAtual = fornecedorService.findById(fornecedorId, empresaId);
			BeanUtils.copyProperties(fornecedorIModel, fornecedorAtual, "id", "dataCadastro", "empresa");
			
			//fornecedorDisassembler.copyToDomainObject(fornecedorIModel, fornecedorAtual);
			return ResponseEntity.status(HttpStatus.OK).body(fornecedorAssembler.toModel(fornecedorService.save(fornecedorAtual, empresaId)));
		} catch(EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{fornecedorId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Integer empresaId, @PathVariable Integer fornecedorId) {
		fornecedorService.ativar(fornecedorId, empresaId);
	}
	
	@DeleteMapping("/{fornecedorId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Integer empresaId, @PathVariable Integer fornecedorId) {
		fornecedorService.inativar(fornecedorId, empresaId);
	}
}
