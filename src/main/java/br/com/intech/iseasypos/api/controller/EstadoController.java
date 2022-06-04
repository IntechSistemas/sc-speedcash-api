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

import br.com.intech.iseasypos.api.assembler.EstadoIModelDisassembler;
import br.com.intech.iseasypos.api.assembler.EstadoModelAssembler;
import br.com.intech.iseasypos.api.model.EstadoModel;
import br.com.intech.iseasypos.api.model.input.EstadoIModel;
import br.com.intech.iseasypos.domain.exception.EstadoNaoEncontradoException;
import br.com.intech.iseasypos.domain.exception.NegocioException;
import br.com.intech.iseasypos.domain.model.Estado;
import br.com.intech.iseasypos.domain.repository.EstadoRepository;
import br.com.intech.iseasypos.domain.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	@Autowired
	private EstadoService estadoService;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private EstadoModelAssembler estadoAssembler;
	@Autowired
	private EstadoIModelDisassembler estadoDisassembler;
	
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public List<EstadoModel> findAll(){
		return estadoAssembler.toCollectionModel(estadoRepository.findAll()); 
	}
	
	@GetMapping("/{estadoId}")
	public EstadoModel findById(@PathVariable Integer estadoId) {
		return estadoAssembler.toModel(estadoService.findById(estadoId));
	}

	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel save(@Valid @RequestBody EstadoIModel estadoIModel){
		Estado estado = estadoDisassembler.toDomainObject(estadoIModel);
		return estadoAssembler.toModel(estadoService.save(estado));
	}
	
	@PutMapping("/{estadoId}")
	public ResponseEntity<?> update(@PathVariable Integer estadoId, @Valid @RequestBody EstadoIModel estadoIModel){
		try {
			Estado estadoAtual = estadoService.findById(estadoId);				
			estadoDisassembler.copyToDomainsObject(estadoIModel, estadoAtual);
			return ResponseEntity.ok(estadoAssembler.toModel(estadoService.save(estadoAtual)));
		}catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer estadoId) {
		estadoService.delete(estadoId);
	}
	
	
}
