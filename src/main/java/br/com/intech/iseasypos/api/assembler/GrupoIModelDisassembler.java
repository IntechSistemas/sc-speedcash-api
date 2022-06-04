package br.com.intech.iseasypos.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.input.GrupoIModel;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Grupo;

@Component
public class GrupoIModelDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo toDomainObject(GrupoIModel grupoIModel) {
		return modelMapper.map(grupoIModel, Grupo.class);
	}
	
	public void copyToDomainObject(GrupoIModel grupoIModel, Grupo grupo) {
		grupo.setEmpresa(new Empresa());
		modelMapper.map(grupoIModel, grupo);
	}
}
