package br.com.intech.iseasypos.infrastructure.model.stonebank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
	private String street;
	private String number;
	private String complement;
	private String neighborhood;
	private String zip_code;
	private String city;
	private String state;
}
