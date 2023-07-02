package com.santander.ib.domain.cliente;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO	{

	private Long id;
	
	private String nome;
	
	private Boolean planoExclusive;
	
	private BigDecimal saldo;
	
	private String numeroconta;
	
	private String dataNascimento;

}
