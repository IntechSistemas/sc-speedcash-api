package br.com.intech.iseasypos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.intech.iseasypos.domain.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {}
