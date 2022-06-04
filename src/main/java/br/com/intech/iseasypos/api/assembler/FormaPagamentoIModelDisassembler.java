package br.com.intech.iseasypos.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.input.FormaPagamentoIModel;
import br.com.intech.iseasypos.domain.model.FormaPagamento;

@Component
public class FormaPagamentoIModelDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamento toDomainObject(FormaPagamentoIModel formaPagamentoIModel) {
		return modelMapper.map(formaPagamentoIModel, FormaPagamento.class);
	}
	
	public void copyToDomainObject(FormaPagamentoIModel formaPagamentoIModel, FormaPagamento formaPagamento) {
		modelMapper.map(formaPagamentoIModel, formaPagamento);
	}

}
