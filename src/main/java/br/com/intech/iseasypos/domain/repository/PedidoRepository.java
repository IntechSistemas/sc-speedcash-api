package br.com.intech.iseasypos.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>, JpaSpecificationExecutor<Pedido>{
	public Optional<Pedido> findByIdAndEmpresa(Integer pedidoId, Empresa empresa);
	public List<Pedido> findByEmpresa(Empresa empresa);
}