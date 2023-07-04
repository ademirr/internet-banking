package com.santander.ib.domain.cliente;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.santander.ib.infra.SaldoInsuficienteException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "clientes",
		uniqueConstraints=
		@UniqueConstraint(columnNames={"numeroConta"}))
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String nome;
	
	private Boolean planoExclusive;
	
    private BigDecimal saldo;
	
    private String numeroConta;
	
	private LocalDate dataNascimento;

	public Cliente(ClienteDTO dto) {
		this.nome = dto.nome();
		this.planoExclusive = dto.planoExclusive();
		this.saldo = dto.saldo();
		this.numeroConta = dto.numeroConta();
		String data = dto.dataNascimento();
        LocalDate ld = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.dataNascimento = ld;
	}
	
	public void depositar(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
    }

    public void sacar(BigDecimal valor) {
        if(valor.compareTo(saldo) <= 0) {
        	this.saldo = this.saldo.subtract(valor);
        } else {
        	throw new SaldoInsuficienteException();
        }
    }


}
