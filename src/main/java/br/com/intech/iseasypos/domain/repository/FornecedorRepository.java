package br.com.intech.iseasypos.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Fornecedor;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
	public Page<Fornecedor> findByEmpresa(Empresa empresas, Pageable pageable);

	public Optional<Fornecedor> findByIdAndEmpresa(Integer fornecedorId, Empresa empresa);

	@Transactional
	@Modifying
	@Query("delete from Fornecedor where id = :fornecedorId and empresa_id = :empresaId")
	public void deleteByIdAndEmpresa(Integer fornecedorId, Integer empresaId);
}
