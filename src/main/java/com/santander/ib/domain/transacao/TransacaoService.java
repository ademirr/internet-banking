package com.santander.ib.domain.transacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository repository;
	
	public Transacao executarTransacao(TransacaoDTO dto) {
		return new Transacao();
	}
}
