package br.com.intech.iseasypos.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.input.FornecedorIModel;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Endereco;
import br.com.intech.iseasypos.domain.model.Fornecedor;

@Component
public class FornecedorIModelDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Fornecedor toDomainObject(FornecedorIModel fornecedorIModel) {
		return modelMapper.map(fornecedorIModel, Fornecedor.class);
	}
	
	public void copyToDomainObject(FornecedorIModel fornecedorIModel, Fornecedor fornecedor) {
		fornecedor.setEmpresa(new Empresa());
		fornecedor.setEndereco(new Endereco());
		modelMapper.map(fornecedorIModel, fornecedor);
	}
}
