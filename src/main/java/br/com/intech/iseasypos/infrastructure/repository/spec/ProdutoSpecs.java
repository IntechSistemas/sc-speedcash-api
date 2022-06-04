package br.com.intech.iseasypos.infrastructure.repository.spec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import br.com.intech.iseasypos.domain.model.Produto;
import br.com.intech.iseasypos.domain.repository.filter.ProdutoFilter;

public class ProdutoSpecs {
	public static Specification<Produto> comFiltro(ProdutoFilter filtro){
		return (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			if(filtro.getEmpresa() != null) {
				predicates.add(builder.equal(root.get("empresa"), filtro.getEmpresa()));
			}
			
			if(!StringUtils.isEmpty(filtro.getCean())) {
				predicates.add(builder.like(builder.upper(root.get("cean")), "%" + filtro.getCean().toUpperCase() + "%"));
			}
			
			if(!StringUtils.isEmpty(filtro.getDescricao())) {
				predicates.add(builder.like(builder.upper(root.get("descricao")), "%"+ filtro.getDescricao().toUpperCase() + "%"));
			}
			
			if(filtro.getFornecedor() != null) {
				predicates.add(builder.equal(root.get("fornecedor"), filtro.getFornecedor()));
			}
			
			if(filtro.getGrupo() != null) {
				predicates.add(builder.equal(root.get("grupo"), filtro.getGrupo()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
