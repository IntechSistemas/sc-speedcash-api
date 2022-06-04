package br.com.intech.iseasypos.api.assembler;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.input.CargoIModel;
import br.com.intech.iseasypos.domain.model.Cargo;
import br.com.intech.iseasypos.domain.model.ConfiguracaoBancaria;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Permissao;

@Component
public class ConfiguracaoBancariaIModelDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	/*public ConfiguracaoBancaria toDomainObject(ConfiguracaoBancariaIModel configIModel) {
		return modelMapper.map(configIModel, ConfiguracaoBancaria.class);
	}
	
	public void copyToDomainObject(ConfiguracaoBancariaIModel configIModel, ConfiguracaoBancaria config) {
		config.setEmpresa(new Empresa());
		modelMapper.map(configIModel, configIModel);
	}*/
}

