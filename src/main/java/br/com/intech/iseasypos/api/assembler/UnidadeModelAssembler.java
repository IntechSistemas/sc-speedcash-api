package br.com.intech.iseasypos.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.UnidadeModel;
import br.com.intech.iseasypos.domain.model.Unidade;

@Component
public class UnidadeModelAssembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public UnidadeModel toModel(Unidade unidade) {
		return modelMapper.map(unidade, UnidadeModel.class);
	}
	public List<UnidadeModel>toCollectionModel(List<Unidade> unidades){
		return unidades.stream() 
			.map(unidade -> toModel(unidade))
			.collect(Collectors.toList());
	}
}
