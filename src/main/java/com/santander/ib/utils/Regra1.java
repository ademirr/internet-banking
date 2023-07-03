package com.santander.ib.utils;

import java.math.BigDecimal;

public class Regra1 implements Taxa {

	@Override
	public BigDecimal calcular(BigDecimal valor) {
		valor = valor.add(valor.multiply(new BigDecimal("0.004")));
		return valor;
	}

}
