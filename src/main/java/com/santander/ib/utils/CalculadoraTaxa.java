package com.santander.ib.utils;

import java.math.BigDecimal;

public class CalculadoraTaxa {
	
	public BigDecimal calcular(BigDecimal valor, Taxa taxa) {
		return taxa.calcular(valor);
	}

}
