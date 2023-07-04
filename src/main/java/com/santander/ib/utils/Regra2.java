package com.santander.ib.utils;

import java.math.BigDecimal;

public class Regra2 implements Taxa {

	@Override
	public BigDecimal calcular(BigDecimal valor, Boolean planoExclusive) {
		if (!planoExclusive) {
			valor = valor.add(valor.multiply(new BigDecimal("0.01")));
		}
		return valor;
	}

}
