package com.santander.ib.domain.transacao;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoDetalhamentoDTO {
		
	String numeroconta;
	
	String tipoTransacao;
	
	BigDecimal valor;
	
	LocalDate dataTransacao;

}
