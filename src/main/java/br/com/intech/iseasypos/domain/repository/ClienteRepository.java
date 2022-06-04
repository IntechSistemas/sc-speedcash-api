package br.com.intech.iseasypos.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.model.Cliente;
import br.com.intech.iseasypos.domain.model.Empresa;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	public List<Cliente> findByEmpresa(Empresa empresas);
	public Optional<Cliente> findByCnpjAndEmpresa(String cnpj, Empresa empresa);
	public Optional<Cliente> findByIdAndEmpresa(Integer clienteId, Empresa empresa);
	@Transactional
	@Modifying
	@Query("delete from Cliente where id = :clienteId and empresa_id = :empresaId")
	public void deleteByIdAndEmpresa(Integer clienteId, Integer empresaId);
}
