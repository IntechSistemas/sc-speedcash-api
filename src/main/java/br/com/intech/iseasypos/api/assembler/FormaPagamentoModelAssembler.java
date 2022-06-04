package br.com.intech.iseasypos.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.FormaPagamentoModel;
import br.com.intech.iseasypos.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
	}
	public List<FormaPagamentoModel> toCollectionModel(List<FormaPagamento> formaPagamentos){
		return formaPagamentos.stream()
				.map(cargo -> toModel(cargo))
				.collect(Collectors.toList());
	}
}
