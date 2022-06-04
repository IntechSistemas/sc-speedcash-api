package br.com.intech.iseasypos.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GrupoIdIModel {
	@NotNull
	private Integer id;
}
