package br.com.intech.iseasypos.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoIdIModel {
	@NotNull
	private Integer id;
}
