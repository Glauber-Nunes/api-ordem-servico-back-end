package com.gnsoftware.Ordem.Servico.controller;

import com.gnsoftware.Ordem.Servico.dto.AtendenteDto;
import com.gnsoftware.Ordem.Servico.services.AtendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/atendentes")
public class AtendenteController {

    @Autowired
    AtendenteService atendenteService;

    @PostMapping
    public ResponseEntity<AtendenteDto> save(@Valid @RequestBody AtendenteDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(atendenteService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtendenteDto> update(@Valid @PathVariable Long id, @RequestBody AtendenteDto dto) {
        return ResponseEntity.ok().body(atendenteService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtendenteDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(atendenteService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<AtendenteDto>> findAll() {
        return ResponseEntity.ok(atendenteService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        atendenteService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Atendente Deletado!");
    }
}
