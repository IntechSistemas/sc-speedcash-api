package br.com.intech.iseasypos.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.intech.iseasypos.api.model.EnderecoModel;
import br.com.intech.iseasypos.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
	//	modelMapper.createTypeMap(Estado.class, EstadoModel.class)
	//		.addMapping(Estado::getCodigo, EstadoModel::setCodigo);
		
		modelMapper.createTypeMap(Endereco.class, EnderecoModel.class)
			.<String>addMapping(
				enderecoSource -> enderecoSource.getCidade().getEstado().getDescricao(),
				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value)
			);
//		modelMapper.createTypeMap(Pagamento.class, PagamentoModel.class)
//		.<String>addMapping(
//			pagamentoSource -> pagamentoSource.getStatus().getDescricao(),
//			(pagamentoModelDest, value) -> pagamentoModelDest.setStatus(value)
//		);
		return modelMapper;
	}
}
