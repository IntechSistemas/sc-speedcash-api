package br.com.intech.iseasypos.domain.service;

import br.com.intech.iseasypos.domain.filter.FormaPagamentoFilter;
import br.com.intech.iseasypos.domain.model.*;
import br.com.intech.iseasypos.infrastructure.model.EstoqueReport;
import br.com.intech.iseasypos.infrastructure.model.VendasPorFormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface RelatorioQueryService extends JpaRepository<Pedido, Integer> {

    @Query("select new br.com.intech.iseasypos.infrastructure.model.VendasPorFormaPagamento(" +
            "p.id as codigo, c as cliente , u as vendedor, p.status as status, p.valorTotal as valor) " +
            "from " +
            "Pedido p " +
            "inner join Cliente c on c.id = p.cliente " +
            "inner join Usuario u on u.id = p.cadastradoPor " +
            "inner join Pagamento pag on pag.pedido = u.id " +
            "inner join FormaPagamento fp on fp.id = pag.formaPagamento " +
            "where " +
            "p.dataCadastro > :inicio AND " +
            "p.dataCadastro < :fim AND " +
            "p.empresa = :empresa"

    )
    List<VendasPorFormaPagamento> relatorioVendasPorFormaPagamento(Empresa empresa, OffsetDateTime inicio, OffsetDateTime fim);

    @Query("select new br.com.intech.iseasypos.infrastructure.model.EstoqueReport(" +
            "p.descricao as descricao, g as grupo , f as fornecedor, p.quantidade as quantidade, u.descricao as unidade, p.valorCompra, p.valorVenda, (p.valorCompra * p.quantidade) as valorTotal, p.servico as servico) " +
            "from " +
            "Produto p " +
            "inner join Grupo g on g.id = p.grupo " +
            "inner join Fornecedor f on f.id = p.fornecedor " +
            "inner join Unidade u on u.id = p.unidade " +
            "where " +
            "p.empresa = :empresa"

    )
    List<EstoqueReport> relatorioEstoque(Empresa empresa);
}
