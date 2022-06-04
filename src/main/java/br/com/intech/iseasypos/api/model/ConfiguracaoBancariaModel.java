package br.com.intech.iseasypos.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfiguracaoBancariaModel {
	private Integer id;
	private String descricao;
	private String token;
	private String secret;
	private String clientId;
	private String oauthKey;
	private String urlApi;
	private String lojaId;
	private String caixaId;
	private String qrCode;
	private String sellerToken;
	private String stonecode;
	private String codigoConvite;
	private String agenciaNumero;
	private String agenciaContaPJ;
	private String hash;
	private String tipoConvenio;
	private Boolean ambienteProducao;
	private Boolean ativo;
	private EmpresaIdModel empresa;
}
