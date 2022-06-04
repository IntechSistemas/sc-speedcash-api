package br.com.intech.iseasypos.infrastructure.model.stonebank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Estabelecimento {
	public Estabelecimento() {}
	private String id;
	private boolean is_establishment_to_production;
	private String legal_name;
	private String business_name;
	private String document_number;
	private String stone_code;
	private Address address;
}