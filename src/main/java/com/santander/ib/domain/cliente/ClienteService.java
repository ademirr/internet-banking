package com.santander.ib.domain.cliente;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
    private ModelMapper modelMapper;

	public Page<ClienteDetalhamentoDTO> listarClientes(Pageable paginacao) {
        return repository
                .findAll(paginacao)
                .map(p -> modelMapper.map(p, ClienteDetalhamentoDTO.class));
    }

    public ClienteDetalhamentoDTO cadastrarCliente(ClienteDTO dto) {
        Cliente cliente = new Cliente(dto);
        cliente = repository.save(cliente);

        return modelMapper.map(cliente, ClienteDetalhamentoDTO.class);
    }

	public ClienteDetalhamentoDTO getReferenceById(Long id) {
		var cliente = repository.getReferenceById(id);
		return modelMapper.map(cliente, ClienteDetalhamentoDTO.class);
	}

}
