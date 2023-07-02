package com.santander.ib.domain.cliente;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "clientes")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
    private Long id;
	
	@NotBlank
	@Setter
	private String nome;
	
	@NotNull
	@Setter
	private Boolean planoExclusive;
	
	@NotNull
    @Positive
    private BigDecimal saldo;
	
	@NotBlank
	@Setter
	private String numeroconta;
	
	@Pattern(regexp = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")
	@Setter
	private String dataNascimento;
	
	public void depositar(BigDecimal valor) {
        this.saldo.add(valor);
    }

    public void sacar(BigDecimal valor) {
        if(valor.compareTo(saldo) >= 0) { 
            if (valor.compareTo(new BigDecimal("100.00")) <= 0 || this.planoExclusive) {
            	this.saldo.subtract(valor);
            } else if (valor.compareTo(new BigDecimal("100.00")) > 0 && valor.compareTo(new BigDecimal("300.00")) <= 0) {
            	this.saldo.subtract(valor.add(valor.multiply(new BigDecimal("0.004"))));
            } else if (valor.compareTo(new BigDecimal("300.00")) > 0) {
            	this.saldo.subtract(valor.add(valor.multiply(new BigDecimal("0.01"))));
            }
        }
    }


}
