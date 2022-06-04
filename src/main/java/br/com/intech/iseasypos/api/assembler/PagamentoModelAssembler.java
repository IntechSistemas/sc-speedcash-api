package br.com.intech.iseasypos.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.PagamentoModel;
import br.com.intech.iseasypos.domain.model.Pagamento;

@Component
public class PagamentoModelAssembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public PagamentoModel toModel(Pagamento pagamento) {
		return modelMapper.map(pagamento, PagamentoModel.class);
	}
	public List<PagamentoModel> toCollectionModel(List<Pagamento> pagamentos){
		return pagamentos.stream()
				.map(pagamento -> toModel(pagamento))
				.collect(Collectors.toList());
	}
}
