package br.com.intech.iseasypos.domain.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.api.assembler.ProdutoModelAssembler;
import br.com.intech.iseasypos.api.model.ProdutoModel;
import br.com.intech.iseasypos.domain.exception.ArquivoNaoEncontradoException;
import br.com.intech.iseasypos.domain.exception.ProdutoNaoEncontradoException;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Fornecedor;
import br.com.intech.iseasypos.domain.model.FotoProduto;
import br.com.intech.iseasypos.domain.model.Grupo;
import br.com.intech.iseasypos.domain.model.Produto;
import br.com.intech.iseasypos.domain.model.Unidade;
import br.com.intech.iseasypos.domain.repository.ProdutoRepository;
import br.com.intech.iseasypos.domain.repository.filter.ProdutoFilter;
import br.com.intech.iseasypos.domain.service.ArquivoStorageService.NovoArquivo;
import br.com.intech.iseasypos.infrastructure.repository.spec.ProdutoSpecs;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private FornecedorService fornecedorService;
	@Autowired
	private GrupoService grupoService;
	@Autowired
	private UnidadeService unidadeService;
	@Autowired
	private ProdutoModelAssembler produtoAssembler;
	@Autowired
	private ArquivoStorageService arquivoService;
	
	public Page<ProdutoModel> findAll(ProdutoFilter filtro, Pageable pageable){
		Page<Produto> produtoPages = produtoRepository.findAll(ProdutoSpecs.comFiltro(filtro), pageable);
		
		List<ProdutoModel> produtosModel = produtoAssembler.toCollectionModel(produtoPages.getContent());
		
		Page<ProdutoModel> produtosModelPage = new PageImpl<>(produtosModel, pageable, produtoPages.getTotalElements());
		return produtosModelPage;
	}
	
	public Produto findById(Integer produtoId, Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		return produtoRepository.findByIdAndEmpresa(produtoId,empresa)
			.orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
	}
	public Produto findByCprod(Integer cprod, Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		return produtoRepository.findByCProdAndEmpresa(cprod,empresa)
			.orElseThrow(() -> new ProdutoNaoEncontradoException(cprod));
	}
	public Page<ProdutoModel> findByCean(String cean, Integer empresaId, Pageable pageable) {
		Empresa empresa = empresaService.findById(empresaId);
		
		Page<Produto> produtosPage = produtoRepository.findByEmpresaAndCeanLike(empresa, "%" + cean + "%", pageable);
		List<ProdutoModel> produtosModel = produtoAssembler.toCollectionModel(produtosPage.getContent());
		return new PageImpl<>(produtosModel, pageable, produtosPage.getTotalElements());
	}
	
	public Integer count(Empresa empresa) {
		return produtoRepository.countByEmpresa(empresa);
	}
	
	@Transactional
	public Produto save(Produto produto, Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		Fornecedor fornecedor = fornecedorService.findById(produto.getFornecedor().getId(), empresaId);
		Grupo grupo = grupoService.findById(produto.getGrupo().getId(), empresa.getId());
		Unidade unidade = unidadeService.findById(produto.getUnidade().getId());
		
		produto.setEmpresa(empresa);
		produto.setFornecedor(fornecedor);
		produto.setGrupo(grupo);
		produto.setUnidade(unidade);

		if(produto.getId() == null)
			produto.setCProd(this.count(empresa)  + 1);
		return produtoRepository.save(produto);
	}
	
	@Transactional
	public void ativar(Integer produtoId, Integer empresaId) {
		Produto produto = findById(produtoId, empresaId);
		produto.ativar();
	}
	
	@Transactional
	public void inativar(Integer produtoId, Integer empresaId) {
		Produto produto = findById(produtoId, empresaId);
		produto.inativar();
	}
		
	public FotoProduto findFotoById(Integer empresaId, Integer produtoId, Integer fotoId) {
		return produtoRepository
			.findFotoById(empresaId, produtoId, fotoId)
			.orElseThrow(() -> new ArquivoNaoEncontradoException(empresaId, produtoId));
	}
	
	@Transactional
	public FotoProduto savePhoto(FotoProduto foto, InputStream dados) {
		foto.setNome(arquivoService.gerarNomeArquivo(foto.getNome()));
		foto = produtoRepository.save(foto);
		produtoRepository.flush();
		NovoArquivo novoArquivo = NovoArquivo.builder()
			.nome(foto.getNome())
			.inputStream(dados)
			.contentType(foto.getContentType())
			.build();
		arquivoService.armazenar(novoArquivo);
		foto.setUrl(arquivoService.recuperar(foto.getNome()).getUrl());
		return foto;
	}
	
	@Transactional
	public void deletePhoto(FotoProduto foto) {
		arquivoService.remover(foto.getNome());
		produtoRepository.delete(foto);
	}
}
