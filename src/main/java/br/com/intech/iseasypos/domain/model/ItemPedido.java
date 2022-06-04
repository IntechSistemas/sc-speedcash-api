package br.com.intech.iseasypos.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "item_pedido")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	@Column(name = "quantidade")
	private Integer quantidade;
	@Column(name = "preco_unitario")
	private Integer precoUnitario;
	@Column(name = "preco_total")
	private Integer precoTotal;
	
	@ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
    
    public void calcularPrecoTotal() {
        Integer precoUnitario = this.getPrecoUnitario();
        Integer quantidade = this.getQuantidade();

        if (precoUnitario == null) {
            precoUnitario = 0;
        }

        if (quantidade == null) {
            quantidade = 0;
        }

        this.setPrecoTotal(precoUnitario * quantidade);
    }
}
