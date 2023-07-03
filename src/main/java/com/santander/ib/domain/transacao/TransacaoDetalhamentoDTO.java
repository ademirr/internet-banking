package com.santander.ib.domain.transacao;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoDetalhamentoDTO {
		
	Long id;
	
	String numeroconta;
	
	String tipoTransacao;
	
	BigDecimal valor;

}
