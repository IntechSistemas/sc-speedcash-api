package br.com.intech.iseasypos.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.intech.iseasypos.api.assembler.UsuarioIModelDisassembler;
import br.com.intech.iseasypos.api.assembler.UsuarioModelAssembler;
import br.com.intech.iseasypos.api.model.UsuarioModel;
import br.com.intech.iseasypos.api.model.input.UsuarioComSenhaIModel;
import br.com.intech.iseasypos.api.model.input.UsuarioIModel;
import br.com.intech.iseasypos.api.model.input.UsuarioSenhaIModel;
import br.com.intech.iseasypos.domain.model.Usuario;
import br.com.intech.iseasypos.domain.service.UsuarioService;

@RestController
@RequestMapping()
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private UsuarioModelAssembler usuarioAssembler;
	@Autowired
	private UsuarioIModelDisassembler usuarioDisassembler;

	@GetMapping("empresa/{empresaId}/usuarios")
	@ResponseStatus(value = HttpStatus.OK)
	public List<UsuarioModel> findAll(@PathVariable Integer empresaId) {
		return usuarioAssembler.toCollectionModel(usuarioService.findAll(empresaId));
	}

	@GetMapping("empresa/{empresaId}/usuarios/{usuarioId}")
	public UsuarioModel findById(@PathVariable Integer empresaId, @PathVariable Integer usuarioId) {
		return usuarioAssembler.toModel(usuarioService.findById(usuarioId, empresaId));
	}

	@PostMapping("empresa/{empresaId}/usuarios")
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel save(@PathVariable Integer empresaId, @Valid @RequestBody UsuarioComSenhaIModel usuarioIModel) {
		Usuario usuario = usuarioDisassembler.toDomainObject(usuarioIModel);
		usuario = usuarioService.save(usuario, empresaId);
		return usuarioAssembler.toModel(usuario);
	}

	@PutMapping("empresa/{empresaId}/usuarios/{usuarioId}")
	public UsuarioModel update(@PathVariable Integer empresaId, @PathVariable Integer usuarioId,
			@Valid @RequestBody UsuarioIModel usuarioIModel) {
		Usuario usuarioAtual = usuarioService.findById(usuarioId, empresaId);
		usuarioDisassembler.copyToDomainObject(usuarioIModel, usuarioAtual);
		usuarioAtual = usuarioService.save(usuarioAtual, empresaId);
		return usuarioAssembler.toModel(usuarioAtual);
	}

	@PutMapping("empresa/{empresaId}/usuarios/{usuarioId}/senha")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void update(@PathVariable Integer empresaId, @PathVariable Integer usuarioId, @RequestBody @Valid UsuarioSenhaIModel senha) {
		usuarioService.alterarSenha(empresaId, usuarioId,  senha.getSenhaAtual(), senha.getNovaSenha());
	}
	
	
	@GetMapping("/usuarios/login")
	public UsuarioModel login(@RequestParam String login, @RequestParam String senha){
		Usuario usuario = usuarioService.login(login, senha);
		return usuarioAssembler.toModel(usuario);
		
	}
	

}
