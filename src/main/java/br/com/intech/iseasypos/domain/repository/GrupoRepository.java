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
import br.com.intech.iseasypos.domain.model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
	public Page<Grupo> findByEmpresa(Empresa empresas, Pageable pageable);
	public Optional<Grupo> findByIdAndEmpresa(Integer grupoId, Empresa empresa);
	@Transactional
	@Modifying
	@Query("delete from Grupo where id = :grupoId and empresa_id = :empresaId")
	public void deleteByIdAndEmpresa(Integer grupoId, Integer empresaId);
}
