package br.com.intech.iseasypos.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "foto_produto")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FotoProduto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "content_type")
	private String contentType;
	@Column(name = "tamanho")
	private Long tamanho;
	@Column(name = "url")
	private String url;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;
}
