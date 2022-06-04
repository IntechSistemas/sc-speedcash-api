package br.com.intech.iseasypos.api.assembler;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.input.CargoIModel;
import br.com.intech.iseasypos.domain.model.Cargo;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Permissao;

@Component
public class CargoIModelDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Cargo toDomainObject(CargoIModel cargoIModel) {
		return modelMapper.map(cargoIModel, Cargo.class);
	}
	
	public void copyToDomainObject(CargoIModel cargoIModel, Cargo cargo) {
		cargo.setEmpresa(new Empresa());
		cargo.setPermissoes(new ArrayList<Permissao>());
		modelMapper.map(cargoIModel, cargo);
	}
}
