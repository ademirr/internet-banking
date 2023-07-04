package com.santander.ib.domain.cliente;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDetalhamentoDTO {
		
	String nome;
	
	Boolean planoExclusive;
	
	BigDecimal saldo;
	
	String numeroConta;
	
	LocalDate dataNascimento;

}
