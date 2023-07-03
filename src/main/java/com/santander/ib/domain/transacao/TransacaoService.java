package com.santander.ib.domain.transacao;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santander.ib.domain.cliente.Cliente;
import com.santander.ib.domain.cliente.ClienteRepository;
import com.santander.ib.utils.CalculadoraTaxa;
import com.santander.ib.utils.Regra1;
import com.santander.ib.utils.Regra2;

@Service
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository repository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
    private ModelMapper modelMapper;
	
	public TransacaoDetalhamentoDTO executarTransacao(TransacaoDTO dto) {
		Transacao transacao = new Transacao(dto);
		transacao = repository.save(transacao);
		Cliente cliente = clienteRepository.findByNumeroConta(transacao.getNumeroconta());
		if (transacao.getTipoTransacao().equals("deposito")) {
			cliente.depositar(transacao.getValor());
		} else {
			BigDecimal valor = transacao.getValor();
			CalculadoraTaxa calc = new CalculadoraTaxa();
            if (valor.compareTo(new BigDecimal("100.00")) > 0 && valor.compareTo(new BigDecimal("300.00")) <= 0) {
            	valor = calc.calcular(valor, new Regra1());
            } else if (valor.compareTo(new BigDecimal("300.00")) > 0) {
            	valor = calc.calcular(valor, new Regra2());
            }
			cliente.sacar(valor);
		}
        return modelMapper.map(transacao, TransacaoDetalhamentoDTO.class);
	}
}
