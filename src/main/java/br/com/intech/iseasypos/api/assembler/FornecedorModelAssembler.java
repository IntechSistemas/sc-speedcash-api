package br.com.intech.iseasypos.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.FornecedorModel;
import br.com.intech.iseasypos.domain.model.Fornecedor;

@Component
public class FornecedorModelAssembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public FornecedorModel toModel(Fornecedor fornecedor) {
		return modelMapper.map(fornecedor, FornecedorModel.class);
	}
	public List<FornecedorModel> toCollectionModel(List<Fornecedor> cargos){
		return cargos.stream()
			.map(fornecedor -> toModel(fornecedor))
			.collect(Collectors.toList());
	}
}
