package br.com.intech.iseasypos.api.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.ProdutoModel;
import br.com.intech.iseasypos.api.model.input.CargoIModel;
import br.com.intech.iseasypos.domain.model.Cargo;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Fornecedor;
import br.com.intech.iseasypos.domain.model.Grupo;
import br.com.intech.iseasypos.domain.model.Permissao;
import br.com.intech.iseasypos.domain.model.Produto;

@Component
public class ProdutoModelAssembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public ProdutoModel toModel(Produto produto) {
		return modelMapper.map(produto, ProdutoModel.class);
	}
	public List<ProdutoModel> toCollectionModel(List<Produto> produtos){
		return produtos.stream()
				.map(produto -> toModel(produto))
				.collect(Collectors.toList());
	}
	public void copyToDomainObject(ProdutoModel produtoIModel, Produto produto) {
		produto.setEmpresa(new Empresa());
		produto.setFornecedor(new Fornecedor());
		produto.setGrupo(new Grupo());
		modelMapper.map(produtoIModel, produto);
	}
}
