package br.com.intech.iseasypos.infrastructure.model;

import br.com.intech.iseasypos.domain.model.Fornecedor;
import br.com.intech.iseasypos.domain.model.Grupo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstoqueReport {
    private String descricao;
    private Grupo grupo;
    private Fornecedor fornecedor;
    private int quantidade;
    private String unidade;
    private int valorcompra;
    private int valorvenda;
    private int valortotal;
    private boolean servico;
}
