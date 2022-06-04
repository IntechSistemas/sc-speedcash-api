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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.intech.iseasypos.api.assembler.ClienteModelAssembler;
import br.com.intech.iseasypos.api.model.ClienteModel;
import br.com.intech.iseasypos.domain.exception.EntidadeNaoEncontradaException;
import br.com.intech.iseasypos.domain.exception.NegocioException;
import br.com.intech.iseasypos.domain.model.Cliente;
import br.com.intech.iseasypos.domain.service.ClienteService;

@RestController
@RequestMapping("/empresa/{empresaId}/clientes")
public class ClienteController {
	@Autowired
	private ClienteService clienteService;
	@Autowired 
	private ClienteModelAssembler clienteAssembler;
	
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public List<ClienteModel> findAll(@PathVariable Integer empresaId){
		return clienteAssembler.toCollectionModel(clienteService.findAll(empresaId)); 
	}
	
	@GetMapping("/{clienteId}")
	public ClienteModel findById(@PathVariable Integer empresaId, @PathVariable Integer clienteId) {
		return clienteAssembler.toModel(clienteService.findById(clienteId, empresaId));
	}
	@GetMapping("/cnpj")
	public ClienteModel findByCnpj(@PathVariable Integer empresaId, @RequestParam String cnpj) {
		return clienteAssembler.toModel(clienteService.findByCnpj(cnpj, empresaId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente save(@PathVariable Integer empresaId, @Valid @RequestBody Cliente cliente){
		try {
			return clienteService.save(cliente, empresaId);
		}catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<?> update(@PathVariable Integer empresaId, @PathVariable Integer clienteId, @Valid @RequestBody Cliente cliente){
		try {
			Cliente clienteAtual = clienteService.findById(clienteId, empresaId);
			BeanUtils.copyProperties(cliente, clienteAtual, "id", "dataCadastro", "empresa");
			clienteAtual = clienteService.save(clienteAtual, empresaId);
			return ResponseEntity.ok(clienteAtual);
		} catch(EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{clienteId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer empresaId, @PathVariable Integer clienteId) {
		clienteService.delete(clienteId, empresaId);
	}
}
