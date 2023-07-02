package com.santander.ib.domain;

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

	public Page<ClienteDTO> listarClientes(Pageable paginacao) {
        return repository
                .findAll(paginacao)
                .map(p -> modelMapper.map(p, ClienteDTO.class));
    }

    public ClienteDTO cadastrarCliente(ClienteDTO dto) {
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        repository.save(cliente);

        return modelMapper.map(cliente, ClienteDTO.class);
    }

}
