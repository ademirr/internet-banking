package com.santander.ib.domain.transacao;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
	
	Page<Transacao> findByDataTransacaoOrderByNumeroConta(LocalDate dataTransacao, Pageable paginacao);

}
