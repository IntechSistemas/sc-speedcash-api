package br.com.intech.iseasypos.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.CargoModel;
import br.com.intech.iseasypos.api.model.ConfiguracaoBancariaModel;
import br.com.intech.iseasypos.domain.model.Cargo;
import br.com.intech.iseasypos.domain.model.ConfiguracaoBancaria;

@Component
public class ConfiguracaoBancariaModelAssembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public ConfiguracaoBancariaModel toModel(ConfiguracaoBancaria config) {
		return modelMapper.map(config, ConfiguracaoBancariaModel.class);
	}
	public List<ConfiguracaoBancariaModel> toCollectionModel(List<ConfiguracaoBancaria> configs){
		return configs.stream()
				.map(config -> toModel(config))
				.collect(Collectors.toList());
	}
}

