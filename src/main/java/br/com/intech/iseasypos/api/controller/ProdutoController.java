package br.com.intech.iseasypos.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.intech.iseasypos.api.assembler.FotoProdutoModelAssembler;
import br.com.intech.iseasypos.api.assembler.ProdutoIModelDisassembler;
import br.com.intech.iseasypos.api.assembler.ProdutoModelAssembler;
import br.com.intech.iseasypos.api.model.FotoProdutoModel;
import br.com.intech.iseasypos.api.model.ProdutoModel;
import br.com.intech.iseasypos.api.model.input.FotoProdutoIModel;
import br.com.intech.iseasypos.api.model.input.ProdutoIModel;
import br.com.intech.iseasypos.domain.exception.EntidadeNaoEncontradaException;
import br.com.intech.iseasypos.domain.exception.NegocioException;
import br.com.intech.iseasypos.domain.model.FotoProduto;
import br.com.intech.iseasypos.domain.model.Produto;
import br.com.intech.iseasypos.domain.repository.filter.ProdutoFilter;
import br.com.intech.iseasypos.domain.service.ArquivoStorageService;
import br.com.intech.iseasypos.domain.service.ArquivoStorageService.ArquivoRecuperado;
import br.com.intech.iseasypos.domain.service.ProdutoService;

@RestController
@RequestMapping("/empresa/{empresaId}/produtos")
public class ProdutoController {
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ProdutoModelAssembler produtoAssembler;
	@Autowired 
	private ProdutoIModelDisassembler produtoDisassembler;
	@Autowired
	private FotoProdutoModelAssembler fotoAssembler;
	@Autowired
	private ArquivoStorageService arquivoService;

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public Page<ProdutoModel> findAll(@PathVariable Integer empresaId, ProdutoFilter filtro,
			@PageableDefault(size = 10) Pageable pageable) {
		filtro.setEmpresa(empresaId);
		return produtoService.findAll(filtro, pageable);

	}


	@GetMapping("/{cprod}")
	public ProdutoModel findByCprod(@PathVariable Integer empresaId, @PathVariable Integer cprod) {
		return produtoAssembler.toModel(produtoService.findByCprod(cprod, empresaId));
	}
	
	@GetMapping("/codigo-barras/{cean}")
	public Page<ProdutoModel> findByCean(@PathVariable Integer empresaId, @PathVariable String cean, Pageable pageable) {
		return produtoService.findByCean(cean, empresaId, pageable);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel save(@PathVariable Integer empresaId, @Valid @RequestBody ProdutoIModel produtoIModel) {
		try {
			Produto produto = produtoDisassembler.toDomainObject(produtoIModel);
			return produtoAssembler.toModel(produtoService.save(produto, empresaId));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{produtoId}")
	public ResponseEntity<?> update(@PathVariable Integer empresaId, @PathVariable Integer produtoId,
			@Valid @RequestBody ProdutoIModel produto) {
		try {
			Produto produtoAtual = produtoService.findById(produtoId, empresaId);
			produtoDisassembler.copyToDomainObject(produto, produtoAtual);
			produtoAtual = produtoService.save(produtoAtual, empresaId);
			return ResponseEntity.ok(produtoAssembler.toModel(produtoAtual));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{produtoId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Integer empresaId, @PathVariable Integer produtoId) {
		produtoService.ativar(produtoId, empresaId);
	}
	
	@DeleteMapping("/{produtoId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Integer empresaId, @PathVariable Integer produtoId) {
		produtoService.inativar(produtoId, empresaId);
	}

	
	
	
	@GetMapping(path = "/{produtoId}/foto/{fotoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModel find(@PathVariable Integer empresaId, @PathVariable Integer produtoId,
			@PathVariable Integer fotoId) {
		FotoProduto fotoProduto = produtoService.findFotoById(empresaId, produtoId, fotoId);

		return fotoAssembler.toModel(fotoProduto);
	}

	@GetMapping(path = "/{produtoId}/foto/{fotoId}")
	public ResponseEntity<?> servirFoto(@PathVariable Integer empresaId,
			@PathVariable Integer produtoId, @PathVariable Integer fotoId,
			@RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto fotoProduto = produtoService.findFotoById(empresaId, produtoId, fotoId);

			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediaTypeAceitas = MediaType.parseMediaTypes(acceptHeader);

			verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypeAceitas);

			ArquivoRecuperado arquivoRecuperado = arquivoService.recuperar(fotoProduto.getNome());

			if (arquivoRecuperado.temUrl()) {
				return ResponseEntity
					.status(HttpStatus.FOUND)
					.header(HttpHeaders.LOCATION, arquivoRecuperado.getUrl())
					.build();
			} else {
				return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG)
						.body(new InputStreamResource(arquivoRecuperado.getInputStream()));
			}
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

	

	@PutMapping(path = "/{produtoId}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel updatePhoto(@PathVariable Integer empresaId, @PathVariable Integer produtoId,
			@Valid FotoProdutoIModel file) throws IOException {

		FotoProduto foto = new FotoProduto();
		foto.setProduto(produtoService.findById(produtoId, empresaId));
		foto.setDescricao(file.getDescricao());
		foto.setContentType(file.getFoto().getContentType());
		foto.setTamanho(file.getFoto().getSize());
		foto.setNome(file.getFoto().getOriginalFilename());

		return fotoAssembler.toModel(produtoService.savePhoto(foto, file.getFoto().getInputStream()));
	}

	@DeleteMapping("/{produtoId}/foto/{fotoId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer empresaId, @PathVariable Integer produtoId, @PathVariable Integer fotoId) {
		produtoService.findById(produtoId, empresaId);
		FotoProduto foto = produtoService.findFotoById(empresaId, produtoId, fotoId);
		produtoService.deletePhoto(foto);
	}
	
	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypeAceitas)
			throws HttpMediaTypeNotAcceptableException {
		boolean compativel = mediaTypeAceitas.stream().anyMatch(mtAceita -> mtAceita.isCompatibleWith(mediaTypeFoto));

		if (!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypeAceitas);
		}
	}

}
