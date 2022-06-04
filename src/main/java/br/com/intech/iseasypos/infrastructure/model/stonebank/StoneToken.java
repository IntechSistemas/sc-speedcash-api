package br.com.intech.iseasypos.infrastructure.model.stonebank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoneToken {
	private boolean success;
	private String error;
	private String msg;
	private String token;
	private Estabelecimento establishment;
}
