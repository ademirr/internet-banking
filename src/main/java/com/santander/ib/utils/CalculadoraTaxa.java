package com.santander.ib.utils;

import java.math.BigDecimal;

public class CalculadoraTaxa {
	
	public BigDecimal calcular(BigDecimal valor, Boolean planoExclusive, Taxa taxa) {
		return taxa.calcular(valor, planoExclusive);
	}

}
