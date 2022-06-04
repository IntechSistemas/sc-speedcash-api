package br.com.intech.iseasypos.api.model;

import java.time.OffsetDateTime;
import java.util.List;

import br.com.intech.iseasypos.domain.model.StatusPedido;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoModel {
	private Integer id;
	private Integer desconto;
	private Integer valorTotal;
	private Integer troco;
	private StatusPedido status;
	private OffsetDateTime dataCadastro;
	private List<ItemPedidoModel> itens;
	private List<PagamentoModel> pagamentos;
	private UsuarioResumoModel cadastradoPor;
	private UsuarioResumoModel confirmadoPor;
	private UsuarioResumoModel canceladoPor;
	private ClienteResumoModel cliente;
	private EmpresaIdModel empresa;
}
