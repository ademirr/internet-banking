package com.santander.ib.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.santander.ib.domain.cliente.ClienteDTO;
import com.santander.ib.domain.cliente.ClienteDetalhamentoDTO;
import com.santander.ib.domain.cliente.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService service;
	
	@PostMapping
    public ResponseEntity<ClienteDetalhamentoDTO> cadastrar(@RequestBody @Valid ClienteDTO dto, UriComponentsBuilder uriBuilder) {
		ClienteDetalhamentoDTO cliente = service.cadastrarCliente(dto);
		URI endereco = uriBuilder.path("/clientes/{id}").buildAndExpand(cliente.getNumeroConta()).toUri();
        return ResponseEntity.created(endereco).body(cliente);
    }
	
	@GetMapping
    public Page<ClienteDetalhamentoDTO> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return service.listarClientes(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDetalhamentoDTO> detalhar(@PathVariable Long id) {
        var cliente = service.getReferenceById(id);
        return ResponseEntity.ok(cliente);
    }

}
