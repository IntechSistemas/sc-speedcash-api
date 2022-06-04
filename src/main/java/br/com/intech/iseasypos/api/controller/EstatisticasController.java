package br.com.intech.iseasypos.api.controller;

import java.time.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import br.com.intech.iseasypos.domain.filter.FormaPagamentoFilter;
import br.com.intech.iseasypos.domain.model.FormaPagamento;
import br.com.intech.iseasypos.domain.service.RelatorioQueryService;
import br.com.intech.iseasypos.infrastructure.model.EstoqueReport;
import br.com.intech.iseasypos.infrastructure.model.VendasPorFormaPagamento;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.intech.iseasypos.domain.filter.TotalVendasMesFilter;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Pedido;
import br.com.intech.iseasypos.domain.model.Produto;
import br.com.intech.iseasypos.domain.model.dto.TotalVendasMes;
import br.com.intech.iseasypos.domain.model.dto.TotalVendasPeriodo;
import br.com.intech.iseasypos.domain.repository.PedidoRepository;
import br.com.intech.iseasypos.domain.repository.ProdutoRepository;
import br.com.intech.iseasypos.domain.service.EmpresaService;
import br.com.intech.iseasypos.domain.service.VendaQueryService;

@RestController
@RequestMapping(path = "/empresa/{idEmpresa}/estatisticas")
public class EstatisticasController {
	@Autowired
	private VendaQueryService vendaService;
	@Autowired
	private RelatorioQueryService relatorioService;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	@GetMapping("/vendas-por-status")
	public List<TotalVendasMes> find(@PathVariable Integer idEmpresa, TotalVendasMesFilter filter){
		Empresa empresa = empresaService.findById(idEmpresa);
		filter.setEmpresa(empresa.getId());
		return vendaService.findTotalVendasMes(filter);
	} 
	
	@GetMapping("/dash-vendas")
	public TotalVendasPeriodo findByDate(@PathVariable Integer idEmpresa){
		Empresa empresa = empresaService.findById(idEmpresa);
	
		List<Pedido> pedidos = pedidoRepository.findByEmpresa(empresa);
		
		TotalVendasPeriodo vendas = new TotalVendasPeriodo();
		for(Pedido p : pedidos) {
			if(p.getStatus().getDescricao().equalsIgnoreCase("CONFIRMADO")) {
				if(p.getDataCadastro().toLocalDate().equals(LocalDate.now()) ) {
					vendas.setVendasDia(p.getValorTotal() + vendas.getVendasDia());
				}
				
				if(p.getDataCadastro().getMonthValue() == OffsetDateTime.now().getMonthValue() &&
				   p.getDataCadastro().getYear() == OffsetDateTime.now().getYear()) {
					vendas.setVendasMes(p.getValorTotal() + vendas.getVendasMes());
				}
				
				if(p.getDataCadastro().getYear() == OffsetDateTime.now().getYear()) {
					vendas.setVendasAno(p.getValorTotal() + vendas.getVendasAno());
				}
			}
		}
		vendas.setTotalProdutos(produtoRepository.countByEmpresa(empresa));
		
		List<Produto> produtos = produtoRepository.findByEmpresa(empresa); 
		
		for(Produto produto : produtos) {
			vendas.setTotalEstoque(vendas.getTotalEstoque() + (produto.getQuantidade() * produto.getValorCompra()));
		}
		return vendas;
	}

	@GetMapping("/vendas-forma-pagamento")
	public ResponseEntity<byte[]> relatorioVendasPorFormaPagamento(@PathVariable Integer idEmpresa, FormaPagamentoFilter filtro) throws JRException {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
		Empresa empresa = empresaService.findById(idEmpresa);
		List<VendasPorFormaPagamento> dados = relatorioService.relatorioVendasPorFormaPagamento(empresa,
				OffsetDateTime.of(filtro.getInicio().getYear(), filtro.getInicio().getMonthValue(), filtro.getInicio().getDayOfMonth(), 0, 0, 0, 0, ZoneOffset.UTC),
				OffsetDateTime.of(filtro.getFim().getYear(), filtro.getFim().getMonthValue(), filtro.getFim().getDayOfMonth(), 23, 59, 59, 0, ZoneOffset.UTC)
		);


		Map<String, Object> parametros = new HashMap<>();
		parametros.put("DT_INICIO", Date.valueOf(filtro.getInicio()));
		parametros.put("DT_FIM", Date.valueOf(filtro.getFim()));
		parametros.put("FORMAPAG", "TODOS");
		parametros.put("REPORT LOCALE", new Locale("pt", "BR"));

		String relatorio = "C:\\Relatorios\\Modelos\\Vendas-por-forma-pagamento.jasper";
		JasperPrint jasperPrint = JasperFillManager.fillReport(relatorio, parametros, new JRBeanCollectionDataSource(dados));

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, org.springframework.http.MediaType.APPLICATION_PDF_VALUE)
				.body(JasperExportManager.exportReportToPdf(jasperPrint));

	}

	@GetMapping("/estoque")
	public ResponseEntity<byte[]> relatorioEstoque(@PathVariable Integer idEmpresa) throws JRException {
		Empresa empresa = empresaService.findById(idEmpresa);
		List<EstoqueReport> dados = relatorioService.relatorioEstoque(empresa);

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("GRUPO", null);
		parametros.put("FORNECEDOR", null);
		parametros.put("REPORT LOCALE", new Locale("pt", "BR"));

		String relatorio = "C:\\Relatorios\\Modelos\\relatorio-estoque.jasper";
		JasperPrint jasperPrint = JasperFillManager.fillReport(relatorio, parametros, new JRBeanCollectionDataSource(dados));

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, org.springframework.http.MediaType.APPLICATION_PDF_VALUE)
				.body(JasperExportManager.exportReportToPdf(jasperPrint));

	}

}
