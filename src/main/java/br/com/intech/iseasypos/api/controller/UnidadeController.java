package br.com.intech.iseasypos.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.intech.iseasypos.api.assembler.UnidadeIModelDisassembler;
import br.com.intech.iseasypos.api.assembler.UnidadeModelAssembler;
import br.com.intech.iseasypos.api.model.UnidadeModel;
import br.com.intech.iseasypos.api.model.input.UnidadeIModel;
import br.com.intech.iseasypos.domain.exception.NegocioException;
import br.com.intech.iseasypos.domain.exception.UnidadeNaoEncontradoException;
import br.com.intech.iseasypos.domain.model.Unidade;
import br.com.intech.iseasypos.domain.repository.UnidadeRepository;
import br.com.intech.iseasypos.domain.service.UnidadeService;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {
	@Autowired
	private UnidadeService unidadeService;
	@Autowired
	private UnidadeRepository unidadeRepository;
	@Autowired
	private UnidadeModelAssembler unidadeAssembler;
	@Autowired
	private UnidadeIModelDisassembler unidadeDisassembler;
	
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public Page<UnidadeModel> findAll(Pageable pageable){
		Page<Unidade> unidadesPage = unidadeRepository.findAll(pageable);
		List<UnidadeModel> unidadesModel = unidadeAssembler.toCollectionModel(unidadeRepository.findAll());
		return new PageImpl<>(unidadesModel, pageable, unidadesPage.getTotalElements());
	}
	
	@GetMapping("/{unidadeId}")
	public UnidadeModel findById(@PathVariable Integer unidadeId) {
		return unidadeAssembler.toModel(unidadeService.findById(unidadeId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UnidadeModel save(@Valid @RequestBody UnidadeIModel unidadeIModel){
		Unidade unidade = unidadeDisassembler.toDomainObject(unidadeIModel);
		return unidadeAssembler.toModel(unidadeService.save(unidade));
		
	}
	
	@PutMapping("/{unidadeId}")
	public ResponseEntity<?> update(@PathVariable Integer unidadeId, @Valid @RequestBody UnidadeIModel unidadeIModel){
		try {
			Unidade unidadeAtual = unidadeService.findById(unidadeId);
			unidadeDisassembler.copyToDomainsObject(unidadeIModel, unidadeAtual);
			return ResponseEntity.ok(unidadeAssembler.toModel(unidadeService.save(unidadeAtual)));
		} catch (UnidadeNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
}
