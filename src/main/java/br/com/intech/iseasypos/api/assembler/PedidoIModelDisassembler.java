package br.com.intech.iseasypos.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.input.PedidoIModel;
import br.com.intech.iseasypos.domain.model.Pedido;

@Component
public class PedidoIModelDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Pedido toDomainObject(PedidoIModel pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }
    
    public void copyToDomainObject(PedidoIModel pedidoIModel, Pedido pedido) {
        modelMapper.map(pedidoIModel, pedido);
    }            
}
