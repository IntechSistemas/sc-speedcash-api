package br.com.intech.iseasypos.infrastructure.model.stonebank.pretransaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {
	private int type;
	private int installment;
	private int installment_type;
}
