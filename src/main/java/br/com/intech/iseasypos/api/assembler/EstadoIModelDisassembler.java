package br.com.intech.iseasypos.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.input.EstadoIModel;
import br.com.intech.iseasypos.domain.model.Estado;

@Component
public class EstadoIModelDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Estado toDomainObject(EstadoIModel estadoIModel) {
		return modelMapper.map(estadoIModel, Estado.class);
	}
	
	public void copyToDomainsObject(EstadoIModel estadoIModel, Estado estado) {
		modelMapper.map(estadoIModel, estado);
	}
}
