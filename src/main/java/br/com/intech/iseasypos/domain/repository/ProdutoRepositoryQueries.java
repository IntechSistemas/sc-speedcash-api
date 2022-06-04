package br.com.intech.iseasypos.domain.repository;

import br.com.intech.iseasypos.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {
	public FotoProduto save(FotoProduto foto);
	public void delete(FotoProduto foto);
}
