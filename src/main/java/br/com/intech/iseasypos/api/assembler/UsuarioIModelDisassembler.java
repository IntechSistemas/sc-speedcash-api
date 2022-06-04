package br.com.intech.iseasypos.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.intech.iseasypos.api.model.input.UsuarioIModel;
import br.com.intech.iseasypos.domain.model.Usuario;

@Component
public class UsuarioIModelDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDomainObject(UsuarioIModel usuarioIModel) {
		return modelMapper.map(usuarioIModel, Usuario.class);
	}
	
	public void copyToDomainObject(UsuarioIModel usuarioIModel, Usuario usuario) {
		modelMapper.map(usuarioIModel, usuario);
	}
}
