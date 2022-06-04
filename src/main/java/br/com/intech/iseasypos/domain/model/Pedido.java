package br.com.intech.iseasypos.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "pedido")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	@Enumerated(EnumType.STRING)
	private StatusPedido status;
	@PositiveOrZero
	private Integer valorTotal = 0;
	@PositiveOrZero
	private Integer desconto = 0;
	@PositiveOrZero
	private Integer troco = 0;
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();
	@OneToMany(mappedBy = "pedido")
	private List<Pagamento> pagamentos = new ArrayList<>();
	@Column(name = "data_cadastro")
	@CreationTimestamp
	private OffsetDateTime dataCadastro;
	@ManyToOne
	@JoinColumn(name = "cadastrado_por")
	private Usuario cadastradoPor;
	@Column(name = "data_confirmacao")
	private OffsetDateTime dataConfirmado;
	@ManyToOne
	@JoinColumn(name = "confirmado_por")
	private Usuario confirmadoPor;
	@Column(name = "data_cancelamento")
	private OffsetDateTime dataCancelado;
	@ManyToOne
	@JoinColumn(name = "cancelado_por")
	private Usuario canceladoPor;
	
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	public void calcularValorTotal() {
	    getItens().forEach(ItemPedido::calcularPrecoTotal);
	    
	    this.valorTotal = 0;
	    for(ItemPedido item : this.getItens()) {
	    	this.valorTotal += item.getPrecoTotal();
	    }
	   
	}
}
