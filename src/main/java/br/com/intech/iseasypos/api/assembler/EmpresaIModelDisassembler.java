package br.com.intech.iseasypos.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.input.EmpresaIModel;
import br.com.intech.iseasypos.domain.model.Cidade;
import br.com.intech.iseasypos.domain.model.Empresa;

@Component
public class EmpresaIModelDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Empresa toDomainObject(EmpresaIModel empresaIModel) {
		return modelMapper.map(empresaIModel, Empresa.class);
	}
	
	public void copyToDomainObject(EmpresaIModel empresaIModel, Empresa empresa) {
		if(empresa.getEndereco() != null) {
			empresa.getEndereco().setCidade(new Cidade());
		}
		modelMapper.map(empresaIModel, empresa);
	}
}
