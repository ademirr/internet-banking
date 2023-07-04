package com.santander.ib.domain.transacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.santander.ib.domain.cliente.Cliente;
import com.santander.ib.domain.cliente.ClienteService;

@SpringBootTest
@AutoConfigureMockMvc
class TransacaoServiceTest {
	
	@Autowired
	private TransacaoService transacaoService;
	
	@MockBean
    private TransacaoRepository repository;
	
	@MockBean
	private ClienteService clienteService;
	
	@Test
	@DisplayName("Deveria devolver valor total igual valor original quando valor original até 100 reais")
	void testAdicionarTaxa() throws Exception {
		String numeroConta = "123456";
		BigDecimal valor = new BigDecimal("100.00");
		Cliente cliente = new Cliente(1l, "Joao", false, new BigDecimal("100.00"), "123456", LocalDate.now());
		when(clienteService.findByNumeroConta(any())).thenReturn(cliente);
		BigDecimal valorTotal = transacaoService.adicionarTaxa(numeroConta, valor);
		assertEquals(new BigDecimal("100.00"), valorTotal);
	}
	
	@Test
	@DisplayName("Deveria devolver valor total acrescentado em 0.4% do valor original quando valor original entre 101 e 300 reais")
	void testAdicionarTaxa2() throws Exception {
		String numeroConta = "123456";
		BigDecimal valor = new BigDecimal("101.00");
		Cliente cliente = new Cliente(1l, "Joao", false, new BigDecimal("100.00"), "123456", LocalDate.now());
		when(clienteService.findByNumeroConta(any())).thenReturn(cliente);
		BigDecimal valorTotal = transacaoService.adicionarTaxa(numeroConta, valor);
		assertEquals(new BigDecimal("101.40"), valorTotal);
	}
	
	@Test
	@DisplayName("Deveria devolver valor total acrescentado em 1% do valor original quando valor original maior que 300 reais")
	void testAdicionarTaxa3() throws Exception {
		String numeroConta = "123456";
		BigDecimal valor = new BigDecimal("301.00");
		Cliente cliente = new Cliente(1l, "Joao", false, new BigDecimal("100.00"), "123456", LocalDate.now());
		when(clienteService.findByNumeroConta(any())).thenReturn(cliente);
		BigDecimal valorTotal = transacaoService.adicionarTaxa(numeroConta, valor);
		assertEquals(new BigDecimal("304.01"), valorTotal);
	}
	
	@Test
	@DisplayName("Deveria devolver valor total igual valor original quando plano exclusive")
	void testAdicionarTaxa4() throws Exception {
		String numeroConta = "123456";
		BigDecimal valor = new BigDecimal("301.00");
		Cliente cliente = new Cliente(1l, "Joao", true, new BigDecimal("100.00"), "123456", LocalDate.now());
		when(clienteService.findByNumeroConta(any())).thenReturn(cliente);
		BigDecimal valorTotal = transacaoService.adicionarTaxa(numeroConta, valor);
		assertEquals(new BigDecimal("301.00"), valorTotal);
	}
	
	@Test
	@DisplayName("Deveria devolver valor total igual valor original quando plano exclusive")
	void testExecutarSaque() throws Exception {
		String numeroConta = "123456";
		BigDecimal valor = new BigDecimal("300.00");
		Cliente cliente = new Cliente(1l, "Joao", true, new BigDecimal("1000.00"), "123456", LocalDate.now());
		when(clienteService.findByNumeroConta(any())).thenReturn(cliente);
		transacaoService.executarSaque(numeroConta, valor);
		assertEquals(new BigDecimal("700.00"), cliente.getSaldo());
	}

}
