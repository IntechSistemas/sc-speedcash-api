package br.com.intech.iseasypos.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.input.ProdutoIModel;
import br.com.intech.iseasypos.domain.model.Fornecedor;
import br.com.intech.iseasypos.domain.model.Grupo;
import br.com.intech.iseasypos.domain.model.Produto;
import br.com.intech.iseasypos.domain.model.Unidade;

@Component
public class ProdutoIModelDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Produto toDomainObject(ProdutoIModel produtoIModel) {
		return modelMapper.map(produtoIModel, Produto.class);
	}
	
	public void copyToDomainObject(ProdutoIModel produtoIModel, Produto produto) {
		produto.setUnidade(new Unidade());
		produto.setFornecedor(new Fornecedor());
		produto.setGrupo(new Grupo());
		modelMapper.map(produtoIModel, produto);
	}
}

