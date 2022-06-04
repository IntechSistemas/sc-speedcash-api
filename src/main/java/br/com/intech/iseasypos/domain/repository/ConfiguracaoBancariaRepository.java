package br.com.intech.iseasypos.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.model.ConfiguracaoBancaria;
import br.com.intech.iseasypos.domain.model.Empresa;

@Repository
public interface ConfiguracaoBancariaRepository extends JpaRepository<ConfiguracaoBancaria, Integer> {
	
	public List<ConfiguracaoBancaria> findByEmpresa(Empresa empresas);
	public Optional<ConfiguracaoBancaria> findByIdAndEmpresa(Integer configId, Empresa empresa);
	@Transactional
	@Modifying
	@Query("delete from ConfiguracaoBancaria where id = :configId and empresa_id = :empresaId")
	public void deleteByIdAndEmpresa(Integer configId, Integer empresaId);
}
