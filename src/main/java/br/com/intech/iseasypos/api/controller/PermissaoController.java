package br.com.intech.iseasypos.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.intech.iseasypos.domain.model.Permissao;
import br.com.intech.iseasypos.domain.repository.PermissaoRepository;
import br.com.intech.iseasypos.domain.service.PermissaoService;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {
	@Autowired
	private PermissaoRepository permissaoRepository;
	@Autowired
	private PermissaoService permissaoService;
	
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public List<Permissao> findAll(){
		return permissaoRepository.findAll(); 
	}
	@GetMapping("/{permissaoId}")
	public Permissao findById(@PathVariable Integer permissaoId) {
		return permissaoService.findById(permissaoId);
	}
}
