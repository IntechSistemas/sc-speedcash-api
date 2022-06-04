package br.com.intech.iseasypos.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.com.intech.iseasypos.domain.model.Empresa;

@Repository
public class EmpresaRepositoryImpl {
	@PersistenceContext
	private EntityManager manager;
	
	public List<Empresa> find(String nome){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Empresa> criteria = builder.createQuery(Empresa.class);
		Root<Empresa> root = criteria.from(Empresa.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if(StringUtils.hasText(nome)) {
			predicates.add(builder.like(root.get("razaoSocial"), "%" + nome + "%"));			
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Empresa> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
}
