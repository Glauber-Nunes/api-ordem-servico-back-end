package com.gnsoftware.Ordem.Servico.controller;

import com.gnsoftware.Ordem.Servico.dto.SituacaoOrdemDto;
import com.gnsoftware.Ordem.Servico.model.SituacaoOrdemEntity;
import com.gnsoftware.Ordem.Servico.services.SituacaoOrdemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/situacao-ordens")
public class SituacaoOrdemController {

    @Autowired
    private SituacaoOrdemService situacaoOrdemService;

    @PutMapping("/{id}")
    public ResponseEntity<SituacaoOrdemDto> update(@PathVariable Long id, @RequestBody SituacaoOrdemDto situacaoOrdemDto) {
        return ResponseEntity.status(HttpStatus.OK).body(situacaoOrdemService.update(id, situacaoOrdemDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SituacaoOrdemDto> findByid(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(situacaoOrdemService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<SituacaoOrdemDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(situacaoOrdemService.findAll());
    }
}
