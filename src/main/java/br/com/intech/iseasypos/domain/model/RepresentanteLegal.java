package br.com.intech.iseasypos.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import br.com.intech.iseasypos.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "representante_legal")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RepresentanteLegal {
	@NotNull(groups = Groups.RepresentanteLegalId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	@NotBlank
	@Size(min = 5, max = 60, message = "O campo {0} deve conter entre 5 e 60 carácteres!")
	@Column(name = "nome")
	private String nome;
	@NotBlank
	@CPF
	@Column(name = "cpf")
	private String cpf;
	@NotBlank
	@Size(max = 100, message = "O campo {0} deve conter no máximo 100 carácteres!")
	@Column(name = "email")
	private String email;
	@NotNull
	@Size(max = 14, message = "O campo {0} deve conter no máximo 14 carácteres! (Formato: (xx)xxxxx-xxxx)")
	@Column(name = "fone")
	private String fone;
}
