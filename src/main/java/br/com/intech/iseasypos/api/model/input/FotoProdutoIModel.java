package br.com.intech.iseasypos.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import br.com.intech.iseasypos.core.validation.FileContentType;
import br.com.intech.iseasypos.core.validation.FileSize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoIModel {
	@NotNull
	@FileSize(max = "500KB")
	@FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	private MultipartFile foto;
	@NotBlank
	private String descricao;
}
