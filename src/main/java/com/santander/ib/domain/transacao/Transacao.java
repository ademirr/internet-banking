package com.santander.ib.domain.transacao;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "transacoes")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transacao {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String numeroconta;
	
	private String tipoTransacao;
	
    private BigDecimal valor;
    
    private LocalDate dataTransacao;
	
	public Transacao(TransacaoDTO dto) {
		this.numeroconta = dto.numeroconta();
		this.tipoTransacao = dto.tipoTransacao();
		this.valor = dto.valor();
		//DateTimeFormatter formatadorBarra = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.dataTransacao = LocalDate.now();//.format(formatadorBarra);
	}

}
