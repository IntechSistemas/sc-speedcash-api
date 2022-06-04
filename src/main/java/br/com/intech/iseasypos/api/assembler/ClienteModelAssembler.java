package br.com.intech.iseasypos.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.ClienteModel;
import br.com.intech.iseasypos.domain.model.Cliente;

@Component
public class ClienteModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ClienteModel toModel(Cliente cliente) {
		return modelMapper.map(cliente, ClienteModel.class);
	}
	public List<ClienteModel> toCollectionModel(List<Cliente> clientes){
		return clientes.stream()
				.map(cliente -> toModel(cliente))
				.collect(Collectors.toList());
	}
}
