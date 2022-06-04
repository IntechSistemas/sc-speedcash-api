package br.com.intech.iseasypos.infrastructure.repository;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.intech.iseasypos.core.stone.StoneProperties;
import br.com.intech.iseasypos.domain.exception.NegocioException;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Pagamento;
import br.com.intech.iseasypos.infrastructure.model.stonebank.Address;
import br.com.intech.iseasypos.infrastructure.model.stonebank.Estabelecimento;
import br.com.intech.iseasypos.infrastructure.model.stonebank.StoneToken;
import br.com.intech.iseasypos.infrastructure.model.stonebank.pretransaction.Payment;
import br.com.intech.iseasypos.infrastructure.model.stonebank.pretransaction.PreTransaction;
import br.com.intech.iseasypos.infrastructure.model.stonebank.retorno.PreTransactionActiveReturn;

@Service
public class StoneBankRepositoryImpl implements StoneBankRepository{
	@Autowired
	private RestTemplate restTemplate;
	@Autowired 
	private StoneProperties stoneProperties;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Override
	public void createPreTransaction(String establishment_id, Pagamento pagamento) {
		HttpHeaders headers = this.settingHeader(this.getToken());
		pagamento.getPedido();
		
		PreTransaction pretransaction = new PreTransaction();
		pretransaction.setAmount(pagamento.getValorPago());
		pretransaction.setEstablishment_id(establishment_id);
		
		Payment payment = new Payment();
		//type: 1 DEBITO - 2 CREDITO - 3 VOUCHER
		
		String formaPagamento = pagamento.getFormaPagamento().getDescricao().toUpperCase();
		
		if(formaPagamento.equals("DÉBITO")) {
			payment.setType(1);
		}else if(formaPagamento.equals("CRÉDITO")) {
			payment.setType(2);
			if(pagamento.getParcelas() > 1) {
				payment.setInstallment(pagamento.getParcelas());
			}
			payment.setInstallment_type(1);//TODO: 1 SEM JUROS - 2 COM JUROS
		}else {
			payment.setType(3);
		}
		pretransaction.setPayment(payment);
		HttpEntity<PreTransaction> entity = new HttpEntity<PreTransaction>(pretransaction, headers);
		
		try {
			PreTransactionActiveReturn retorno = restTemplate.exchange(stoneProperties.getLink() + "/pre-transaction/create",
					HttpMethod.POST, entity, PreTransactionActiveReturn.class).getBody();
			
			pagamento.setTransacaoId(retorno.getPre_transaction().getPre_transaction_id());
		}catch (Exception e) {
			throw new NegocioException("Problema na API Stone Connect");
		}
	}
	
	public String cadastrarEstabelecimento(Empresa empresa, String stoneCode) {
		HttpHeaders headers = this.settingHeader(this.getToken());
		
		Estabelecimento estabelecimento = new Estabelecimento();
		
		estabelecimento.set_establishment_to_production(true);
		estabelecimento.setLegal_name(empresa.getRazaoSocial());
		estabelecimento.setBusiness_name(empresa.getNomeFantasia() == null || empresa.getNomeFantasia().isEmpty() ? empresa.getRazaoSocial() : empresa.getNomeFantasia()); //TODO: 
		estabelecimento.setDocument_number(empresa.getCnpj().replaceAll("\\D", ""));
		estabelecimento.setStone_code(stoneCode);

		Address endereco = new Address();
		endereco.setStreet(empresa.getEndereco().getLogradouro());
		endereco.setNumber(empresa.getEndereco().getNumero());
		endereco.setComplement(empresa.getEndereco().getComplemento());
		endereco.setNeighborhood(empresa.getEndereco().getBairro());
		endereco.setZip_code(empresa.getEndereco().getCep());
		endereco.setCity(empresa.getEndereco().getCidade().getDescricao());
		endereco.setState(empresa.getEndereco().getCidade().getEstado().getSigla());
		estabelecimento.setAddress(endereco);


		HttpEntity<Estabelecimento> entity = new HttpEntity<Estabelecimento>(estabelecimento, headers);

		StoneToken stoneToken = restTemplate
				.exchange(stoneProperties.getLink() + "/establishment/create-existing-stone", HttpMethod.POST,
						entity, StoneToken.class)
				.getBody();

		return stoneToken.getEstablishment().getId();

	}

	private HttpHeaders settingHeader(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", "Bearer " + token);

		return headers;
	}
	private String getToken() {
		System.out.println(this.stoneProperties.getCode());
		HttpHeaders headers = this.settingHeader(this.stoneProperties.getCode());
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		try {
			StoneToken stoneToken = restTemplate
					.exchange(stoneProperties.getLink() + "/token", HttpMethod.GET, entity, StoneToken.class)
					.getBody();
			return stoneToken.getToken();
		}catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	
}
