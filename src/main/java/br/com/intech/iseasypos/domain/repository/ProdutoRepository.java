package br.com.intech.iseasypos.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.FotoProduto;
import br.com.intech.iseasypos.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>,
	JpaSpecificationExecutor<Produto>, ProdutoRepositoryQueries{
	
	public List<Produto> findByEmpresa(Empresa empresas);
	public Optional<Produto> findByIdAndEmpresa(Integer produtoId, Empresa empresa);
	public Optional<Produto> findByCProdAndEmpresa(Integer cprod, Empresa empresa);
	public Page<Produto> findByEmpresaAndCeanLike(Empresa empresa, String cean, Pageable pageable);
	public Integer countByEmpresa(Empresa empresa);
	@Transactional
	@Modifying
	@Query("delete from Produto where id = :produtoId and empresa_id = :empresaId")
	public void deleteByIdAndEmpresa(Integer produtoId, Integer empresaId);
	
	@Query("select f from FotoProduto f join f.produto p "
			+ "where p.empresa.id = :empresaId and f.produto.id = :produtoId and f.id = :fotoId")
	Optional<FotoProduto> findFotoById(Integer empresaId, Integer produtoId, Integer fotoId);
}
