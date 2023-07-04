package com.santander.ib.utils;

import java.math.BigDecimal;

public class Regra1 implements Taxa {

	@Override
	public BigDecimal calcular(BigDecimal valor, Boolean planoExclusive) {
		if (!planoExclusive) {
			valor = valor.add(valor.multiply(new BigDecimal("0.004")));
		}
		return valor;
	}

}
