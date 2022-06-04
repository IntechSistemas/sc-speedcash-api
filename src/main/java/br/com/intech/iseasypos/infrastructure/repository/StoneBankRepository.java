package br.com.intech.iseasypos.infrastructure.repository;

import org.springframework.stereotype.Repository;

import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Pagamento;

@Repository
public interface StoneBankRepository{
	public void createPreTransaction(String establishment_id, Pagamento pagamento);
	public String cadastrarEstabelecimento(Empresa empresa, String stoneCode);
}
