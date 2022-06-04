package br.com.intech.iseasypos.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.CargoModel;
import br.com.intech.iseasypos.domain.model.Cargo;

@Component
public class CargoModelAssembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public CargoModel toModel(Cargo cargo) {
		return modelMapper.map(cargo, CargoModel.class);
	}
	public List<CargoModel> toCollectionModel(List<Cargo> cargos){
		return cargos.stream()
				.map(cargo -> toModel(cargo))
				.collect(Collectors.toList());
	}
}
