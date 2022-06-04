package br.com.intech.iseasypos.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.input.UnidadeIModel;
import br.com.intech.iseasypos.domain.model.Unidade;

@Component
public class UnidadeIModelDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Unidade toDomainObject(UnidadeIModel unidadeIModel) {
		return modelMapper.map(unidadeIModel, Unidade.class);
	}
	
	public void copyToDomainsObject(UnidadeIModel unidadeIModel, Unidade unidade) {
		modelMapper.map(unidadeIModel, unidade);
	}
}
