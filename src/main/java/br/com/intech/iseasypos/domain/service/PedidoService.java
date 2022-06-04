package br.com.intech.iseasypos.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.intech.iseasypos.api.assembler.PedidoModelAssembler;
import br.com.intech.iseasypos.api.model.PedidoModel;
import br.com.intech.iseasypos.domain.exception.NegocioException;
import br.com.intech.iseasypos.domain.exception.PedidoNaoEncontradoException;
import br.com.intech.iseasypos.domain.model.Cidade;
import br.com.intech.iseasypos.domain.model.Cliente;
import br.com.intech.iseasypos.domain.model.ConfiguracaoBancaria;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Endereco;
import br.com.intech.iseasypos.domain.model.Pagamento;
import br.com.intech.iseasypos.domain.model.Pedido;
import br.com.intech.iseasypos.domain.model.Pix;
import br.com.intech.iseasypos.domain.model.Produto;
import br.com.intech.iseasypos.domain.model.StatusPagamento;
import br.com.intech.iseasypos.domain.model.StatusPedido;
import br.com.intech.iseasypos.domain.model.Usuario;
import br.com.intech.iseasypos.domain.repository.PedidoRepository;
import br.com.intech.iseasypos.domain.repository.filter.PedidoFilter;
import br.com.intech.iseasypos.infrastructure.repository.StoneBankRepository;
import br.com.intech.iseasypos.infrastructure.repository.spec.PedidoSpecs;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private PagamentoService pagamentoService;
	@Autowired
	private FormaPagamentoService formaService;	
	@Autowired
	private StoneBankRepository bancoRepository;
	
	@Autowired
	private PedidoModelAssembler pedidoAssembler;

	public Pedido findById(Integer pedidoId, Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		return pedidoRepository.findByIdAndEmpresa(pedidoId, empresa)
				.orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
	}

	public Page<PedidoModel> findAllPage(PedidoFilter filtro, Pageable pageable) {
		Page<Pedido> pedidoPages = pedidoRepository.findAll(PedidoSpecs.comFiltro(filtro), pageable);
		List<PedidoModel> pedidosModel = pedidoAssembler.toCollectionModel(pedidoPages.getContent());
		Page<PedidoModel> pedidosModelPage = new PageImpl<>(pedidosModel, pageable, pedidoPages.getTotalElements());
		return pedidosModelPage;

	}

	@Transactional
	public Pedido emitir(Pedido pedido) {
		validarPedido(pedido);
		validarItens(pedido);

		pedido.calcularValorTotal();
		return pedidoRepository.save(pedido);
	}
	
	@Transactional
	public Pedido pagar(List<Pagamento> pagamentos, Integer pedidoId, Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		Pedido pedido = this.findById(pedidoId, empresaId);
		pedido.calcularValorTotal();
		ConfiguracaoBancaria configbancaria = empresa.getConfigsBancarias()
			.stream()
			.filter(config -> config.getDescricao().toUpperCase().equals("STONEBANK"))
			.findFirst()
			.orElseThrow(() -> new NegocioException("Não há configurações Stone para a empresa responsável pelo pedido!")); 
		
		pagamentos.forEach(pagamento -> {
			pagamento.setValorPago(pagamento.getValorPago() - (pagamento.getValorPago() * (pedido.getDesconto() / 100)));
			pagamento.setPedido(pedido);
			pagamento.setStatus(StatusPagamento.PENDENTE);
			
			if(empresa.aceitaFormaPagamento(pagamento.getFormaPagamento())) {
				pagamento.setFormaPagamento(formaService.findById(pagamento.getFormaPagamento().getId()));
			}else {
				throw new NegocioException(String.format("Forma de Pagamento não aceite pela empresa!"));
			}
			
			if(pagamento.getFormaPagamento().getDescricao().toUpperCase().equals("DINHEIRO".toUpperCase())) {
				pagamento.setStatus(StatusPagamento.PAGO);
			} else if(pagamento.getFormaPagamento().getDescricao().toUpperCase().equals("PIX".toUpperCase())) {
				pagamento.setStatus(StatusPagamento.PAGO);
				Pix pix = new Pix();
				pagamento.setPix(pix);
				//TODO: AJUSTAR PIX
			}else {
				if(empresa.getUsaStoneApi()) {
					validarPagamentoStone(configbancaria.getClientId(), pagamento);
				}else {
					validarPagamento(pagamento);
				}
			}
			pagamento = pagamentoService.save(pagamento);
		});
		
		return pedido;
	}
	private void validarPagamento(Pagamento pagamento) {
		if (pagamento.getCartao() != null) {
			pagamento.getCartao().setCnpj(pagamento.getCartao().getBandeira().getCnpj());
			pagamento.setStatus(StatusPagamento.PAGO);
		}
	}
	
	private void validarPagamentoStone(String establishment_id, Pagamento pagamento) {
		bancoRepository.createPreTransaction(establishment_id, pagamento);
	}

	private void validarItens(Pedido pedido) {
		pedido.getItens().forEach(item -> {
	        Produto produto = produtoService.findById(item.getProduto().getId(), pedido.getEmpresa().getId());
	        item.setPedido(pedido);
	        item.setProduto(produto);
	        item.setPrecoUnitario(produto.getValorVenda());
	    });
	}

	private void validarPedido(Pedido pedido) {
		Empresa empresa = empresaService.findById(pedido.getEmpresa().getId());
		Usuario cadastradoPor = usuarioService.findById(pedido.getCadastradoPor().getId(), pedido.getEmpresa().getId());
		Usuario canceladoPor = pedido.getCanceladoPor() == null ? null
				: usuarioService.findById(pedido.getCanceladoPor().getId(), pedido.getEmpresa().getId());
		Usuario confirmadoPor = pedido.getConfirmadoPor() == null ? null
				: usuarioService.findById(pedido.getConfirmadoPor().getId(), pedido.getEmpresa().getId());
		Cliente cliente = null;
		
		if(pedido.getCliente() != null) {
			if(pedido.getCliente().getId() != null) {
				cliente = clienteService.findById(pedido.getCliente().getId(), pedido.getEmpresa().getId());
			}else {
				try {
					cliente = clienteService.findByCnpj(pedido.getCliente().getCnpj(), pedido.getEmpresa().getId());
				}catch (Exception e) {
					cliente = salvarCliente(pedido.getCliente(), pedido.getEmpresa());
				}
			}
		}
		pedido.setEmpresa(empresa);
		pedido.setCadastradoPor(cadastradoPor);
		pedido.setCanceladoPor(canceladoPor);
		pedido.setCliente(cliente);
		pedido.setConfirmadoPor(confirmadoPor);
		pedido.setStatus(StatusPedido.PENDENTE);

	}

	private Cliente salvarCliente(Cliente cliente, Empresa empresa) {
		if(cliente.getEmail() == null) {
			cliente.setEmail("email@email.com");
		}
		if(cliente.getEmpresa() == null) {
			cliente.setEmpresa(empresa);
		}
		if(cliente.getEndereco() == null) {
			Cidade cidade = new Cidade();
			cidade.setId(1068);
			Endereco endereco = new Endereco();
			endereco.setBairro("meu bairro");
			endereco.setCep("59.300-000");
			endereco.setCidade(cidade);
			endereco.setLogradouro("minha rua");
			endereco.setNumero("SN");
			
			cliente.setEndereco(endereco);

		}
		if(cliente.getFone() == null) {
			cliente.setFone("(84)99999-8888");
		}
		return clienteService.save(cliente, empresa.getId());
	}

}
