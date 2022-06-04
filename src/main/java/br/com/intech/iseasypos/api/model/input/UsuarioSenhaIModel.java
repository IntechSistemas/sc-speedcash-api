package br.com.intech.iseasypos.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioSenhaIModel {
	@NotBlank
    private String senhaAtual;
    
    @NotBlank
    private String novaSenha;
}
