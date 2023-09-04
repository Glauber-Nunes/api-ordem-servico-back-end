package com.gnsoftware.Ordem.Servico.controller;

import com.gnsoftware.Ordem.Servico.model.StatusOrdemServicoEntity;
import com.gnsoftware.Ordem.Servico.services.StatusOrdemServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/status-ordens")
public class StatusOrdemServicoController {
    @Autowired
    private StatusOrdemServicoService service;

    @GetMapping("/{id}")
    public ResponseEntity<StatusOrdemServicoEntity> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<StatusOrdemServicoEntity>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }
    @PutMapping("/{id}")
    public ResponseEntity<StatusOrdemServicoEntity> update(@PathVariable Long id, @RequestBody StatusOrdemServicoEntity ordemServico) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, ordemServico));
    }
}
