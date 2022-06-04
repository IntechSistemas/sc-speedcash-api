package br.com.intech.iseasypos.domain.model;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "usuario")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "login")
	private String login;
	@Column(name = "senha")
	private String senha;
	@Column(name = "email")
	private String email;
	@Column(name = "ativo")
	private Boolean ativo;
	@Column(name = "desconto_maximo")
	private Integer descontoMaximo;
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	@NotNull
	@Valid
	@ManyToMany
	@JoinTable(name = "usuario_cargo", joinColumns = @JoinColumn(name = "usuario_id")
		, inverseJoinColumns = @JoinColumn(name = "cargo_id"))
	private List<Cargo> cargos;	
	@CreationTimestamp
	@Column(name = "data_cadastro")
	private OffsetDateTime dataCadastro;
	@UpdateTimestamp
	@Column(name = "data_atualizacao")
	private OffsetDateTime dataAtualizacao;
	
	public boolean senhaCoincideCom(String senha) {
	    return getSenha().equals(senha);
	}

	public boolean senhaNaoCoincideCom(String senha) {
	    return !senhaCoincideCom(senha);
	}
}