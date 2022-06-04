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

import br.com.intech.iseasypos.api.assembler.CidadeIModelDisassembler;
import br.com.intech.iseasypos.api.assembler.CidadeModelAssembler;
import br.com.intech.iseasypos.api.model.CidadeModel;
import br.com.intech.iseasypos.api.model.input.CidadeIModel;
import br.com.intech.iseasypos.domain.exception.EstadoNaoEncontradoException;
import br.com.intech.iseasypos.domain.exception.NegocioException;
import br.com.intech.iseasypos.domain.model.Cidade;
import br.com.intech.iseasypos.domain.repository.CidadeRepository;
import br.com.intech.iseasypos.domain.service.CidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	@Autowired
	private CidadeService cidadeService;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private CidadeModelAssembler cidadeAssembler;
	@Autowired
	private CidadeIModelDisassembler cidadeDisassembler;
	
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public List<CidadeModel> findAll(){
		List<Cidade> cidades = cidadeRepository.findAll();
		return cidadeAssembler.toCollectionModel(cidades);
	} 
	
	@GetMapping("/{cidadeId}")
	public CidadeModel findById(@PathVariable Integer cidadeId) {
		Cidade cidade = cidadeService.findById(cidadeId);
		return cidadeAssembler.toModel(cidade);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel save(@Valid @RequestBody CidadeIModel cidadeIModel){
		Cidade cidade = cidadeDisassembler.toDomainObject(cidadeIModel);
		cidade = cidadeService.save(cidade);
		return cidadeAssembler.toModel(cidade);
	}
	
	@PutMapping("/{cidadeId}")
	public ResponseEntity<?> update(@PathVariable Integer cidadeId, @Valid @RequestBody CidadeIModel cidadeIModel){
		try {
			Cidade cidadeAtual = cidadeService.findById(cidadeId);
			cidadeDisassembler.copyToDomainObject(cidadeIModel, cidadeAtual);
			
			return ResponseEntity.ok(cidadeAssembler.toModel(cidadeService.save(cidadeAtual)));
		}catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer cidadeId) {
		cidadeService.delete(cidadeId);
	}
	
	
}
