package com.santander.ib.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(ContaJaExisteException.class)
    public ResponseEntity<String> tratarErroContaJaExistente() {
        return ResponseEntity.unprocessableEntity().body("Conta já cadastrada no sistema");
    }
	
	@ExceptionHandler(ContaNaoExisteException.class)
    public ResponseEntity<String> tratarErroContaNaoExistente() {
        return ResponseEntity.unprocessableEntity().body("Conta inexistente");
    }
	
	@ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<String> tratarErroSaldoInsuficiente() {
        return ResponseEntity.unprocessableEntity().body("Saldo Insuficiente");
    }
	
	@ExceptionHandler(DataInvalidaException.class)
    public ResponseEntity<String> tratarErroDataInvalida() {
        return ResponseEntity.unprocessableEntity().body("Data inválida");
    }
}
