package br.com.intech.iseasypos.infrastructure.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import br.com.intech.iseasypos.domain.filter.FormaPagamentoFilter;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.infrastructure.model.VendasPorFormaPagamento;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import br.com.intech.iseasypos.domain.filter.TotalVendasMesFilter;
import br.com.intech.iseasypos.domain.model.Pedido;
import br.com.intech.iseasypos.domain.model.dto.TotalVendasMes;
import br.com.intech.iseasypos.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService{
	@PersistenceContext
	private EntityManager manager;
	@Override
	public List<TotalVendasMes> findTotalVendasMes(TotalVendasMesFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<TotalVendasMes> query = builder.createQuery(TotalVendasMes.class);
		Root<Pedido> root = query.from(Pedido.class);
		
		CompoundSelection<TotalVendasMes> selection = builder.construct(TotalVendasMes.class,
			root.get("status"), builder.sum(root.get("valorTotal")));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(filter.getDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCadastro"), filter.getDe()));
		}
		if(filter.getAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCadastro"), filter.getAte()));
		}
		
		
		query.select(selection);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(root.get("status"));
		
		
		return manager.createQuery(query).getResultList();
	}


//	@Override
//	public List<VendasPorFormaPagamento> relatorioVendasPorFormaPagamento(FormaPagamentoFilter filtro, Empresa empresa) {
//		CriteriaBuilder builder = manager.getCriteriaBuilder();
//
//		CriteriaQuery<VendasPorFormaPagamento> query = builder.createQuery(VendasPorFormaPagamento.class);
//
//		Root<Pedido> root = query.from(Pedido.class);
//
//		query.select(builder.construct(VendasPorFormaPagamento.class,
//				root.get("id"),
//				root.get("status"),
//				root.get("valor_total"),
//				root.get("cadastrado_por"),
//				root.get("cliente")));
//
//		Predicate[] predicates = createRestrictions(filtro, empresa, builder, root);
//
//		query.where(predicates);
//
//		List<Order> orderList = new ArrayList();
//
//		orderList.add(builder.asc(root.get("cliente").get("nome")));
//		orderList.add(builder.asc(root.get("cadastrado_por")));
//		orderList.add(builder.asc(root.get("status")));
//		orderList.add(builder.desc(root.get("valorTotal")));
//		orderList.add(builder.asc(root.get("formaPagamento")));
//		query.orderBy(orderList);
//
//		TypedQuery<VendasPorFormaPagamento> typedQuery = manager.createQuery(query);
//
//		return typedQuery.getResultList();
//	}

	private Predicate[] createRestrictions(FormaPagamentoFilter filtro, Empresa empresa,
										   CriteriaBuilder builder, Root<Pedido> root) {
		List<Predicate> predicates = new ArrayList<>();

		// FILTRO POR EMPRESA
		if (empresa.getId() > 0) {
			predicates.add(
					builder.equal(root.get("usuario").get("empresa").get("id"), empresa.getId()));
		}
		// FILTRO POR DATA DE
		if (filtro.getInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("data_criacao"), filtro.getInicio()));
		}
		// FILTRO POR DATA ATE
		if (filtro.getFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("data_criacao"), filtro.getFim()));
		}

		// FILTRO POR FORMA PAGAMENTO
		System.out.println(filtro.getForma());
		if (!StringUtils.isEmpty(filtro.getForma()) && !filtro.getForma().equals("TODOS")) {
			predicates.add(builder.equal(root.get("formapagamento"), filtro.getForma()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);

	}
}
