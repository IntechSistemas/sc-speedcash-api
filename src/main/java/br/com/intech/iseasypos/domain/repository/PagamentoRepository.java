package br.com.intech.iseasypos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.intech.iseasypos.domain.model.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>, JpaSpecificationExecutor<Pagamento> {}
