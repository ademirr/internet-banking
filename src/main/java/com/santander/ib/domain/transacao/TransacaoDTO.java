package com.santander.ib.domain.transacao;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransacaoDTO(

		@NotBlank
		String numeroconta,
		
		@NotBlank
		String tipoTransacao,
		
		@NotNull
	    @Positive
		BigDecimal valor) {

	}
