package br.com.intech.iseasypos.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.input.PagamentoIModel;
import br.com.intech.iseasypos.domain.model.Pagamento;

@Component
public class PagamentoIModelDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Pagamento toDomainObject(PagamentoIModel pagamentoInput) {
        return modelMapper.map(pagamentoInput, Pagamento.class);
    }
    
    public void copyToDomainObject(PagamentoIModel pagamentoIModel, Pagamento pagamento) {
        modelMapper.map(pagamentoIModel, pagamento);
    }            
}
