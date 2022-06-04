package br.com.intech.iseasypos.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.FotoProdutoModel;
import br.com.intech.iseasypos.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public FotoProdutoModel toModel(FotoProduto fotoProduto) {
		return modelMapper.map(fotoProduto, FotoProdutoModel.class);
	}
	public List<FotoProdutoModel> toCollectionModel(List<FotoProduto> fotoProdutos){
		return fotoProdutos.stream()
				.map(fotoProduto -> toModel(fotoProduto))
				.collect(Collectors.toList());
	}
}
