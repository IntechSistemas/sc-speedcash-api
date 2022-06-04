package br.com.intech.iseasypos.infrastructure.model.stonebank.retorno;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreTransactionResponse {
	private String pre_transaction_token;
	private String pre_transaction_id;
	private String status;
}
