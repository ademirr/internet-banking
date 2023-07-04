package com.santander.ib.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Regra2 implements Taxa {

	@Override
	public BigDecimal calcular(BigDecimal valor, Boolean planoExclusive) {
		if (!planoExclusive) {
			valor = valor.add(valor.multiply(new BigDecimal("0.01"))).setScale(2, RoundingMode.HALF_EVEN);
		}
		return valor;
	}

}
