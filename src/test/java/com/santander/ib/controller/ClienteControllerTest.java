/**
 * 
 */
package com.santander.ib.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.santander.ib.domain.cliente.ClienteDTO;
import com.santander.ib.domain.cliente.ClienteService;
import com.santander.ib.infra.ContaJaExisteException;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ClienteControllerTest {

	@Autowired
	private MockMvc mvc;
    
    @Autowired
    private JacksonTester<ClienteDTO> clienteDTOJson;

    @Autowired
    private JacksonTester<ClienteDTO> clienteDTOResponseJson;
	
	@MockBean
	private ClienteService clienteServiceMock;
	
	@Test
	@DisplayName("Deveria devolver 400 quando está sem corpo")
	void testCadastrar() throws Exception {
		var response = mvc.perform(post("/clientes"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@DisplayName("Deveria devolver 400 quando está com campos em branco")
	void testCadastrar2() throws Exception {
		var clienteDTO = new ClienteDTO(null, null, null, null, null);

        var response = mvc
                .perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteDTOJson.write(clienteDTO).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@DisplayName("Deveria devolver 201 quando executado com sucesso")
	void testCadastrar3() throws Exception {
		var clienteDTO = new ClienteDTO("Joao", false, new BigDecimal("100.00"), "123456", "10/02/1987");
		
		when(clienteServiceMock.cadastrarCliente(any())).thenReturn(new ClienteDTO("Joao", false, new BigDecimal("100.00"), "123456", "10/02/1987"));
		
        var response = mvc
                .perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteDTOJson.write(clienteDTO).getJson()))
                .andReturn().getResponse();

        var clienteDTOResponse = new ClienteDTO("Joao", false, new BigDecimal("100.00"), "123456", "10/02/1987");
        var jsonEsperado = clienteDTOResponseJson.write(clienteDTOResponse).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
        
	}
	
	@Test
	@DisplayName("Deveria devolver 422 quando ocorrer exceção de validação")
	void testCadastrar4() throws Exception {
		var clienteDTO = new ClienteDTO("João", false, new BigDecimal("100.00"), "123456", "10/02/1987");
		
		Mockito.doThrow(new ContaJaExisteException()).when(clienteServiceMock).cadastrarCliente(any());

        var response = mvc
                .perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteDTOJson.write(clienteDTO).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
        assertThat(response.getContentAsString()).isEqualTo("Conta já cadastrada no sistema");
	}

}
