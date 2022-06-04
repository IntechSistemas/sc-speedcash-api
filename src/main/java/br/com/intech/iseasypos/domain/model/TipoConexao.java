package br.com.intech.iseasypos.domain.model;

import lombok.Getter;

@Getter
public enum TipoConexao {
	NENHUM(0, "Nenhum"),
	GER7(1, "Ger7"),
	PAYGO(2, "PayGo"),
	REDEFLEX(3, "Redeflex"),
	ITI(4, "ITI"),
	STONE(5, "Stone"),
	QRCODE(6, "QrCode");
	
	private Integer codigo;
	private String descricao;
	
	TipoConexao(Integer codigo, String descricao) {
		this.codigo = codigo;
        this.descricao = descricao;
	}
}
