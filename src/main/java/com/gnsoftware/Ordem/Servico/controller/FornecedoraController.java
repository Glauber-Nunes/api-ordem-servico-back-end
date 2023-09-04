package com.gnsoftware.Ordem.Servico.controller;

import com.gnsoftware.Ordem.Servico.dto.FornecedorDto;
import com.gnsoftware.Ordem.Servico.model.FornecedorEntity;
import com.gnsoftware.Ordem.Servico.services.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/fornecedores")
public class FornecedoraController {
    @Autowired
    private FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<FornecedorDto> save(@Valid @RequestBody FornecedorDto fornecedorDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorService.save(fornecedorDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorDto> update(@PathVariable Long id, @RequestBody FornecedorDto fornecedorDto) {
        return ResponseEntity.status(HttpStatus.OK).body(fornecedorService.update(id, fornecedorDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        fornecedorService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("AtendenteEntity Deletado");
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(fornecedorService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<FornecedorDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(fornecedorService.findAll());
    }

}
