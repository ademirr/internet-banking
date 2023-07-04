package com.santander.ib.domain.transacao;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransacaoDTO(

		@NotBlank
		String numeroConta,
		
		TipoTransacao tipoTransacao,
		
		@NotNull
	    @Positive
		BigDecimal valor,
		
		LocalDate dataTransacao) {
	
		public TransacaoDTO(Transacao t) {
			this(t.getNumeroConta(), t.getTipoTransacao(), t.getValor(), t.getDataTransacao());
		}

	}
