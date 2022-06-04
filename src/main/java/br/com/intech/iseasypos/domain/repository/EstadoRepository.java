package br.com.intech.iseasypos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.intech.iseasypos.domain.model.Estado;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{}
