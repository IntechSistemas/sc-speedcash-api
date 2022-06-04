package br.com.intech.iseasypos.infrastructure.model.stonebank.retorno;

import br.com.intech.iseasypos.infrastructure.model.stonebank.StoneToken;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreTransactionActiveReturn extends StoneToken{
	private PreTransactionResponse pre_transaction;
}
