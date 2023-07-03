package com.santander.ib.utils;

import java.math.BigDecimal;

public class Regra2 implements Taxa {

	@Override
	public BigDecimal calcular(BigDecimal valor) {
		valor = valor.add(valor.multiply(new BigDecimal("0.01")));
		return valor;
	}

}
