package com.santander.ib.domain.cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteServiceTest {
	
	@Autowired
	private ClienteService clienteService;
	
	@MockBean
    private ClienteRepository repository;
	
	@Test
	@DisplayName("Deveria devolver um objeto ClienteDTO igual ao objeto passado por parametro quando cadastrado com sucesso")
	void testCadastrarCliente() throws Exception {
		ClienteDTO clienteDTO = new ClienteDTO("Joao", false, new BigDecimal("100.00"), "123456", "10/02/1987");
		when(repository.findByNumeroConta(any())).thenReturn(null);
		when(repository.save(any())).thenReturn(new Cliente(clienteDTO));
		ClienteDTO cliente = clienteService.cadastrarCliente(clienteDTO);
		assertEquals(clienteDTO, cliente);
	}

}
