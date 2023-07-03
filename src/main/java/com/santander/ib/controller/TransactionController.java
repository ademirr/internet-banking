package com.santander.ib.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.santander.ib.domain.transacao.TransacaoDTO;
import com.santander.ib.domain.transacao.TransacaoDetalhamentoDTO;
import com.santander.ib.domain.transacao.TransacaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("transacoes")
public class TransactionController {
	
	@Autowired
	private TransacaoService service;
	
	@PostMapping
	@Transactional(isolation=Isolation.REPEATABLE_READ)
	public ResponseEntity<TransacaoDetalhamentoDTO> executarTransacao(@RequestBody @Valid TransacaoDTO transacaoDTO, UriComponentsBuilder uriBuilder) {
		TransacaoDetalhamentoDTO transacao = service.executarTransacao(transacaoDTO);
		URI endereco = uriBuilder.path("/transacoes/{id}").buildAndExpand(transacao.getId()).toUri();
		return ResponseEntity.created(endereco).body(transacao);
	}
}