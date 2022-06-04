package br.com.intech.iseasypos.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.model.Cargo;
import br.com.intech.iseasypos.domain.model.Empresa;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer>  {
	public List<Cargo> findByEmpresa(Empresa empresas);
	public Optional<Cargo> findByIdAndEmpresa(Integer cargoId, Empresa empresa);
	@Transactional
	@Modifying
	@Query("delete from Cargo where id = :cargoId and empresa_id = :empresaId")
	public void deleteByIdAndEmpresa(Integer cargoId, Integer empresaId);
}
