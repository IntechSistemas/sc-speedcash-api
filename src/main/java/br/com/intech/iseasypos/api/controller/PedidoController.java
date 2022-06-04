package br.com.intech.iseasypos.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.intech.iseasypos.api.assembler.PagamentoIModelDisassembler;
import br.com.intech.iseasypos.api.assembler.PedidoIModelDisassembler;
import br.com.intech.iseasypos.api.assembler.PedidoModelAssembler;
import br.com.intech.iseasypos.api.model.PedidoModel;
import br.com.intech.iseasypos.api.model.input.PagamentoIModel;
import br.com.intech.iseasypos.api.model.input.PedidoIModel;
import br.com.intech.iseasypos.domain.exception.EntidadeNaoEncontradaException;
import br.com.intech.iseasypos.domain.exception.NegocioException;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Pagamento;
import br.com.intech.iseasypos.domain.model.Pedido;
import br.com.intech.iseasypos.domain.repository.filter.PedidoFilter;
import br.com.intech.iseasypos.domain.service.PedidoService;

@RestController
@RequestMapping("/empresas/{empresaId}/pedidos")
public class PedidoController {
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private PedidoModelAssembler pedidoAssembler;
	@Autowired
	private PedidoIModelDisassembler pedidoDisassembler;
	@Autowired
	private PagamentoIModelDisassembler pagamentoDisassembler;

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public Page<PedidoModel> findAll(@PathVariable Integer empresaId, PedidoFilter filtro,
			@PageableDefault(size = 1) Pageable pageable) {
		filtro.setEmpresa(empresaId);
		return pedidoService.findAllPage(filtro, pageable);

	}

	@GetMapping("/{pedidoId}")
	public PedidoModel findById(@PathVariable Integer empresaId, @PathVariable Integer pedidoId) {
		return pedidoAssembler.toModel(pedidoService.findById(pedidoId, empresaId));
	}

	@PostMapping
	public PedidoModel save(@PathVariable Integer empresaId, @Valid @RequestBody PedidoIModel pedidoIModel) {
		try {
			
			Pedido novoPedido = pedidoDisassembler.toDomainObject(pedidoIModel);
			
			novoPedido.setEmpresa(new Empresa());
			novoPedido.getEmpresa().setId(empresaId);
			novoPedido = pedidoService.emitir(novoPedido);
			return pedidoAssembler.toModel(novoPedido);
		}catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	@PostMapping("/{pedidoId}/pagamento")
	public PedidoModel pagarPedido(@PathVariable Integer empresaId, @PathVariable Integer pedidoId, @RequestBody List<PagamentoIModel> pagamentosIModel ) {
		List<Pagamento> pagamentos = pagamentosIModel.stream()
				.map(pagamentoIModel -> pagamentoDisassembler.toDomainObject(pagamentoIModel))
				.collect(Collectors.toList());
		
		return pedidoAssembler.toModel(pedidoService.pagar(pagamentos, pedidoId, empresaId));
	}
	
}
