package br.com.intech.iseasypos.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.input.CidadeIModel;
import br.com.intech.iseasypos.domain.model.Cidade;
import br.com.intech.iseasypos.domain.model.Estado;

@Component
public class CidadeIModelDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade toDomainObject(CidadeIModel cidadeIModel) {
		return modelMapper.map(cidadeIModel, Cidade.class);
	}
	
	public void copyToDomainObject(CidadeIModel cidadeIModel, Cidade cidade) {
		cidade.setEstado(new Estado());
		modelMapper.map(cidadeIModel, cidade);
	}
}
