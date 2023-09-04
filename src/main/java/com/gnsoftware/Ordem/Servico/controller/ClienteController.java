package com.gnsoftware.Ordem.Servico.controller;

import com.gnsoftware.Ordem.Servico.dto.ClienteDto;
import com.gnsoftware.Ordem.Servico.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDto> save(@Valid @RequestBody ClienteDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> update(@PathVariable Long id, @Valid @RequestBody ClienteDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente Deletado!");
    }
}
