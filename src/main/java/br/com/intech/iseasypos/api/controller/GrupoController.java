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

import br.com.intech.iseasypos.api.assembler.GrupoIModelDisassembler;
import br.com.intech.iseasypos.api.assembler.GrupoModelAssembler;
import br.com.intech.iseasypos.api.model.GrupoModel;
import br.com.intech.iseasypos.api.model.input.GrupoIModel;
import br.com.intech.iseasypos.domain.exception.EmpresaNaoEncontradaException;
import br.com.intech.iseasypos.domain.exception.NegocioException;
import br.com.intech.iseasypos.domain.model.Grupo;
import br.com.intech.iseasypos.domain.service.GrupoService;

@RestController
@RequestMapping("/empresa/{empresaId}/grupos")
public class GrupoController {
	@Autowired
	private GrupoService grupoService;
	@Autowired
	private GrupoModelAssembler grupoAssembler;
	@Autowired
	private GrupoIModelDisassembler grupoDisassembler;
	
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public Page<GrupoModel> findAll(@PathVariable Integer empresaId, Pageable pageable){
		Page<Grupo> gruposPage = grupoService.findAll(empresaId, pageable); 
		List<GrupoModel> gruposModel = grupoAssembler.toCollectionModel(gruposPage.getContent());
		Page<GrupoModel> gruposModelPage = new PageImpl<>(gruposModel, pageable, gruposPage.getTotalElements());
		return gruposModelPage;
	}
	
	@GetMapping("/{grupoId}")
	public GrupoModel findById(@PathVariable Integer empresaId, @PathVariable Integer grupoId) {
		return grupoAssembler.toModel(grupoService.findById(grupoId, empresaId));
	}
	
	@PostMapping
	public GrupoModel save(@PathVariable Integer empresaId, @Valid @RequestBody GrupoIModel grupoRequest){
		try {
			Grupo grupo = grupoDisassembler.toDomainObject(grupoRequest);
			return grupoAssembler.toModel(grupoService.save(grupo, empresaId));
		}catch (EmpresaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{grupoId}")
	public ResponseEntity<?> update(@PathVariable Integer empresaId, @PathVariable Integer grupoId, @Valid @RequestBody GrupoIModel grupo){
		try {
			Grupo grupoAtual = grupoService.findById(grupoId, empresaId);
			BeanUtils.copyProperties(grupo, grupoAtual, "id", "dataCadastro", "empresa");
			grupoAtual = grupoService.save(grupoAtual, empresaId);
			return ResponseEntity.ok(grupoAssembler.toModel(grupoAtual));
		} catch(EmpresaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{grupoId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Integer empresaId, @PathVariable Integer grupoId) {
		grupoService.ativar(grupoId, empresaId);
	}
	
	@DeleteMapping("/{grupoId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Integer empresaId, @PathVariable Integer grupoId) {
		grupoService.inativar(grupoId, empresaId);
	}
}