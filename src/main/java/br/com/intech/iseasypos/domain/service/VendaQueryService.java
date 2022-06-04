package br.com.intech.iseasypos.domain.service;

import java.util.List;

import br.com.intech.iseasypos.domain.filter.FormaPagamentoFilter;
import br.com.intech.iseasypos.domain.filter.TotalVendasMesFilter;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.dto.TotalVendasMes;
import br.com.intech.iseasypos.infrastructure.model.VendasPorFormaPagamento;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VendaQueryService {
	List<TotalVendasMes> findTotalVendasMes(TotalVendasMesFilter filter);


}
