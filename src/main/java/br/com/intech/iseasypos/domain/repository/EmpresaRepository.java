package br.com.intech.iseasypos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.intech.iseasypos.domain.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer>{
	//public List<Empresa> findByEmpresa(Empresa empresas);
	//public Optional<Empresa> findByIdAndEmpresa(Integer cargoId, Empresa empresa);
	//@Transactional
	//@Modifying
	//@Query("update from Empresa set  where id = :cargoId and empresa_id = :empresaId")
	//public void deleteByIdAndEmpresa(Integer cargoId, Integer empresaId);
}
