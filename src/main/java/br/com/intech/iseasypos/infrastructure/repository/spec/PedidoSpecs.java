package br.com.intech.iseasypos.infrastructure.repository.spec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import br.com.intech.iseasypos.domain.model.Pedido;
import br.com.intech.iseasypos.domain.model.StatusPedido;
import br.com.intech.iseasypos.domain.repository.filter.PedidoFilter;

public class PedidoSpecs {
	public static Specification<Pedido> comFiltro(PedidoFilter filtro){
		return (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			if(filtro.getEmpresa() != null) {
				predicates.add(builder.equal(root.get("empresa"), filtro.getEmpresa()));
			}
			
			if(!StringUtils.isEmpty(filtro.getCadastradoPor())) {
				predicates.add(builder.like(builder.upper(root.get("cadastradoPor").get("nome")), "%" + filtro.getCadastradoPor().toUpperCase() + "%"));
			}
			if(!StringUtils.isEmpty(filtro.getStatus()) && EnumUtils.isValidEnum(StatusPedido.class, filtro.getStatus())) {
				predicates.add(builder.equal(builder.upper(root.get("status")), StatusPedido.valueOf(filtro.getStatus().toUpperCase())));
			}
			
			/*if(!StringUtils.isEmpty(filtro.getDescricao())) {
				predicates.add(builder.like(builder.upper(root.get("descricao")), "%"+ filtro.getDescricao().toUpperCase() + "%"));
			}
			
			if(filtro.getFornecedor() != null) {
				predicates.add(builder.equal(root.get("fornecedor"), filtro.getFornecedor()));
			}
			
			if(filtro.getGrupo() != null) {
				predicates.add(builder.equal(root.get("grupo"), filtro.getGrupo()));
			}*/
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
