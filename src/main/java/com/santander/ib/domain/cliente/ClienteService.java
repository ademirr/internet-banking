package com.santander.ib.domain.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.santander.ib.infra.ContaJaExisteException;
import com.santander.ib.infra.ContaNaoExisteException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;

	public Page<ClienteDTO> listarClientes(Pageable paginacao) {
        return repository
                .findAll(paginacao)
                .map(p -> new ClienteDTO(p));
    }

    public ClienteDTO cadastrarCliente(ClienteDTO dto) {
    	this.verificaContaJaCadastrada(dto.numeroConta());
        Cliente cliente = new Cliente(dto);
        cliente = repository.save(cliente);
        return new ClienteDTO(cliente);
    }

	public ClienteDTO getByConta(String conta) {
		var cliente = repository.findByNumeroConta(conta);
		if (cliente == null) {
			throw new ContaNaoExisteException();
		}
		return new ClienteDTO(cliente);
	}

	public Cliente findByNumeroConta(String numeroconta) {
		return repository.findByNumeroConta(numeroconta);
	}
	
	public void verificaContaJaCadastrada(String numeroconta) {
		Cliente cliente = repository.findByNumeroConta(numeroconta);
		if (cliente != null) {
			throw new ContaJaExisteException();
		}
	}

}
