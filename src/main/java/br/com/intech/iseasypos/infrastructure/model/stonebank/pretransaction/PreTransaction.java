package br.com.intech.iseasypos.infrastructure.model.stonebank.pretransaction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PreTransaction {
	private int amount;
	private String establishment_id;
	private String pos_reference_id;
	private Payment payment;
	private String information_title;
}
