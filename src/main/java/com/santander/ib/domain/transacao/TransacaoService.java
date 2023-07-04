package com.santander.ib.domain.transacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.santander.ib.domain.cliente.Cliente;
import com.santander.ib.domain.cliente.ClienteService;
import com.santander.ib.infra.ContaNaoExisteException;
import com.santander.ib.utils.CalculadoraTaxa;
import com.santander.ib.utils.Regra1;
import com.santander.ib.utils.Regra2;

@Service
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository repository;
	
	@Autowired
	private ClienteService clienteService;
	
	public TransacaoDTO cadastrarTransacao(TransacaoDTO dto, TipoTransacao tipo) {
		Transacao transacao = new Transacao(dto, tipo);
		transacao = repository.save(transacao);
		return new TransacaoDTO(transacao);
	}
	
	public BigDecimal adicionarTaxa(String numeroConta, BigDecimal valor) {
		Cliente cliente = verificarCliente(numeroConta);
		CalculadoraTaxa calc = new CalculadoraTaxa();
        if (valor.compareTo(new BigDecimal("100.00")) > 0 && valor.compareTo(new BigDecimal("300.00")) <= 0) {
        	valor = calc.calcular(valor, cliente.getPlanoExclusive(),  new Regra1());
        } else if (valor.compareTo(new BigDecimal("300.00")) > 0) {
        	valor = calc.calcular(valor, cliente.getPlanoExclusive(), new Regra2());
        }
        return valor;
	}
	
	public void executarSaque(String numeroConta, BigDecimal valor) {
		Cliente cliente = verificarCliente(numeroConta);
		cliente.sacar(valor);
	}
	
	public void executarDeposito(TransacaoDTO dto) {
		Cliente cliente = verificarCliente(dto.numeroConta());
		cliente.depositar(dto.valor());
	}

	private Cliente verificarCliente(String numeroConta) {
		Cliente cliente = clienteService.findByNumeroConta(numeroConta);
		if (cliente == null) {
			throw new ContaNaoExisteException();
		}
		return cliente;
	}

	public Page<TransacaoDTO> listarTransacoes(String dataTransacao, Pageable paginacao) {
        LocalDate ld = LocalDate.parse(dataTransacao, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return repository
                .findByDataTransacaoOrderByNumeroConta(ld, paginacao)
                .map(p -> new TransacaoDTO(p));
    }
}
