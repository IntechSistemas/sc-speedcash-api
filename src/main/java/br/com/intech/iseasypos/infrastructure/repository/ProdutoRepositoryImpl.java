package br.com.intech.iseasypos.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.model.FotoProduto;
import br.com.intech.iseasypos.domain.repository.ProdutoRepositoryQueries;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries{
	@PersistenceContext
	private EntityManager manager;

	@Transactional
	@Override
	public FotoProduto save(FotoProduto foto) {
		return manager.merge(foto);
	}

	@Override
	public void delete(FotoProduto foto) {
		manager.remove(foto);
		
	}
}
