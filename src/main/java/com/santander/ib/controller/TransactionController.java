package com.santander.ib.controller;

import java.math.BigDecimal;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.santander.ib.domain.transacao.TipoTransacao;
import com.santander.ib.domain.transacao.TransacaoDTO;
import com.santander.ib.domain.transacao.TransacaoService;
import com.santander.ib.infra.DataInvalidaException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("transacoes")
public class TransactionController {
	
	@Autowired
	private TransacaoService service;
	
	@PostMapping("/sacar")
	@Transactional(isolation=Isolation.REPEATABLE_READ)
	public ResponseEntity<TransacaoDTO> executarSaque(@RequestBody @Valid TransacaoDTO transacaoDTO, UriComponentsBuilder uriBuilder) {
		TransacaoDTO transacao = service.cadastrarTransacao(transacaoDTO, TipoTransacao.SAQUE);
		BigDecimal valor = service.adicionarTaxa(transacaoDTO.numeroConta(), transacao.valor());
		service.executarSaque(transacaoDTO.numeroConta(), valor);
		URI endereco = uriBuilder.path("/transacoes/{numeroConta}").buildAndExpand(transacao.numeroConta()).toUri();
		return ResponseEntity.created(endereco).body(transacao);
	}
	
	@PostMapping("/depositar")
	@Transactional(isolation=Isolation.REPEATABLE_READ)
	public ResponseEntity<TransacaoDTO> executarDeposito(@RequestBody @Valid TransacaoDTO transacaoDTO, UriComponentsBuilder uriBuilder) {
		transacaoDTO = service.cadastrarTransacao(transacaoDTO, TipoTransacao.DEPOSITO);
		service.executarDeposito(transacaoDTO);
		URI endereco = uriBuilder.path("/transacoes/{numeroConta}").buildAndExpand(transacaoDTO.numeroConta()).toUri();
		return ResponseEntity.created(endereco).body(transacaoDTO);
	}
	
	@GetMapping
    public Page<TransacaoDTO> listar(@RequestParam String dataTransacao, @PageableDefault(size = 10) Pageable paginacao) {
        boolean b = dataTransacao.matches("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
		if (!b) {
			throw new DataInvalidaException();
		}
        return service.listarTransacoes(dataTransacao, paginacao);
    }
}