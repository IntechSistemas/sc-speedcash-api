package br.com.intech.iseasypos.infrastructure.model;

import br.com.intech.iseasypos.domain.model.Cliente;
import br.com.intech.iseasypos.domain.model.StatusPedido;
import br.com.intech.iseasypos.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VendasPorFormaPagamento {
    private int codigo;
    private Cliente cliente;
    private Usuario vendedor;
    private StatusPedido status;
    private int valor;
}
