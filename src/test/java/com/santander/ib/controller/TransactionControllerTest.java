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

import com.santander.ib.domain.transacao.TransacaoDTO;
import com.santander.ib.domain.transacao.TransacaoService;
import com.santander.ib.infra.ContaNaoExisteException;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TransactionControllerTest {

	@Autowired
	private MockMvc mvc;
    
    @Autowired
    private JacksonTester<TransacaoDTO> transacaoDTOJson;

    @Autowired
    private JacksonTester<TransacaoDTO> transacaoDTOResponseJson;
	
	@MockBean
	private TransacaoService transacaoServiceMock;
	
	@Test
	@DisplayName("Deveria devolver 400 quando está sem corpo")
	void testCadastrar() throws Exception {
		var response = mvc.perform(post("/transacoes/sacar"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@DisplayName("Deveria devolver 400 quando está com campos em branco")
	void testCadastrar2() throws Exception {
		var transacaoDTO = new TransacaoDTO(null, null);
		
        var response = mvc
                .perform(post("/transacoes/sacar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoDTOJson.write(transacaoDTO).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@DisplayName("Deveria devolver 201 quando executado com sucesso")
	void testCadastrar3() throws Exception {
		var transacaoDTO = new TransacaoDTO("123456", new BigDecimal("100.00"));
		
		when(transacaoServiceMock.cadastrarTransacao(any(), any())).thenReturn(new TransacaoDTO("123456", new BigDecimal("100.00")));
		
        var response = mvc
                .perform(post("/transacoes/sacar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoDTOJson.write(transacaoDTO).getJson()))
                .andReturn().getResponse();

        var transacaoDTOResponse = new TransacaoDTO("123456", new BigDecimal("100.00"));
        var jsonEsperado = transacaoDTOResponseJson.write(transacaoDTOResponse).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
        
	}
	
	@Test
	@DisplayName("Deveria devolver 422 quando ocorrer exceção de validação")
	void testCadastrar4() throws Exception {
		var transacaoDTO = new TransacaoDTO("123456", new BigDecimal("100.00"));
		
		when(transacaoServiceMock.cadastrarTransacao(any(), any())).thenReturn(new TransacaoDTO("123456", new BigDecimal("100.00")));
		Mockito.doThrow(new ContaNaoExisteException()).when(transacaoServiceMock).adicionarTaxa(any(), any());

        var response = mvc
                .perform(post("/transacoes/sacar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoDTOJson.write(transacaoDTO).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
        assertThat(response.getContentAsString()).isEqualTo("Conta inexistente");
	}
	
	@Test
	@DisplayName("Deveria devolver 400 quando está sem corpo")
	void testCadastrarDeposito() throws Exception {
		var response = mvc.perform(post("/transacoes/depositar"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@DisplayName("Deveria devolver 400 quando está com campos em branco")
	void testCadastrarDeposito2() throws Exception {
		var transacaoDTO = new TransacaoDTO(null, null);
		
        var response = mvc
                .perform(post("/transacoes/depositar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoDTOJson.write(transacaoDTO).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@DisplayName("Deveria devolver 201 quando executado com sucesso")
	void testCadastrarDeposito3() throws Exception {
		var transacaoDTO = new TransacaoDTO("123456", new BigDecimal("100.00"));
		
		when(transacaoServiceMock.cadastrarTransacao(any(), any())).thenReturn(new TransacaoDTO("123456", new BigDecimal("100.00")));
		
        var response = mvc
                .perform(post("/transacoes/depositar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoDTOJson.write(transacaoDTO).getJson()))
                .andReturn().getResponse();

        var transacaoDTOResponse = new TransacaoDTO("123456", new BigDecimal("100.00"));
        var jsonEsperado = transacaoDTOResponseJson.write(transacaoDTOResponse).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
        
	}
	
	@Test
	@DisplayName("Deveria devolver 422 quando ocorrer exceção de validação")
	void testCadastrarDeposito4() throws Exception {
		var transacaoDTO = new TransacaoDTO("123456", new BigDecimal("100.00"));
		
		//when(transacaoServiceMock.cadastrarTransacao(any(), any())).thenReturn(new TransacaoDTO("123456", new BigDecimal("100.00")));
		Mockito.doThrow(new ContaNaoExisteException()).when(transacaoServiceMock).executarDeposito(any());

        var response = mvc
                .perform(post("/transacoes/depositar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transacaoDTOJson.write(transacaoDTO).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
        assertThat(response.getContentAsString()).isEqualTo("Conta inexistente");
	}

}
