package com.gnsoftware.Ordem.Servico.controller;

import com.gnsoftware.Ordem.Servico.dto.ClienteDto;
import com.gnsoftware.Ordem.Servico.dto.OrdemServicoDto;
import com.gnsoftware.Ordem.Servico.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @PostMapping
    public ResponseEntity<ClienteDto> save(@Valid @RequestBody ClienteDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> update(@Valid @PathVariable Long id, @Valid @RequestBody ClienteDto dto) {

        return ResponseEntity.status(HttpStatus.OK).body(clienteService.update(id, dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findById(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @GetMapping
    public ResponseEntity<List<ClienteDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findAll());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente Deletado!");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @GetMapping("/count")
    public ResponseEntity<Long> countCliente(){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.countCliente());
    }
}
