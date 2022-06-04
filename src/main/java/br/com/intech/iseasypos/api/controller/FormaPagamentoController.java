package br.com.intech.iseasypos.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.intech.iseasypos.api.assembler.FormaPagamentoIModelDisassembler;
import br.com.intech.iseasypos.api.assembler.FormaPagamentoModelAssembler;
import br.com.intech.iseasypos.api.model.FormaPagamentoModel;
import br.com.intech.iseasypos.api.model.input.FormaPagamentoIModel;
import br.com.intech.iseasypos.domain.model.FormaPagamento;
import br.com.intech.iseasypos.domain.repository.FormaPagamentoRepository;
import br.com.intech.iseasypos.domain.service.FormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
	@Autowired
	private FormaPagamentoService formaService;
	@Autowired
	private FormaPagamentoRepository formaRepository;
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoAssembler;
	@Autowired
	private FormaPagamentoIModelDisassembler formaPagamentoDisassembler;
	
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public List<FormaPagamentoModel> findAll(){
		List<FormaPagamento> formaPagamentos = formaRepository.findAll();
		return formaPagamentoAssembler.toCollectionModel(formaPagamentos);
	}
	
	@GetMapping("/{formaPagamentoId}")
	public FormaPagamento findById(@PathVariable Integer formaPagamentoId) {
		return formaService.findById(formaPagamentoId);
	}
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public FormaPagamentoModel save(@Valid @RequestBody FormaPagamentoIModel formaPagamentoIModel) {
		FormaPagamento formaPagamento = formaPagamentoDisassembler.toDomainObject(formaPagamentoIModel);
		formaPagamento = formaService.save(formaPagamento);
		return formaPagamentoAssembler.toModel(formaPagamento);
	}
	
	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoModel update(@PathVariable Integer formaPagamentoId, @Valid @RequestBody FormaPagamentoIModel formaPagamentoIModel){
			FormaPagamento formaPagamentoAtual = this.findById(formaPagamentoId);
			formaPagamentoDisassembler.copyToDomainObject(formaPagamentoIModel, formaPagamentoAtual);
			return formaPagamentoAssembler.toModel(formaService.save(formaPagamentoAtual));
		
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer formaPagamentoId) {
		formaService.delete(formaPagamentoId);
	}
	
}
