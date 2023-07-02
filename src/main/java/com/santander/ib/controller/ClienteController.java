package com.santander.ib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.santander.ib.domain.ClienteDTO;
import com.santander.ib.domain.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService service;
	
	@PostMapping
    public ResponseEntity<ClienteDTO> cadastrar(@RequestBody @Valid ClienteDTO dto, UriComponentsBuilder uriBuilder) {
        ClienteDTO cliente = service.cadastrarCliente(dto);

        return ResponseEntity.created(null).body(cliente);
    }
	
	@GetMapping
    public Page<ClienteDTO> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return service.listarClientes(paginacao);
    }

}
