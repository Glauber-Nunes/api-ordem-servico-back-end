package com.gnsoftware.Ordem.Servico.controller;

import com.gnsoftware.Ordem.Servico.dto.TecnicoDto;
import com.gnsoftware.Ordem.Servico.model.TecnicoEntity;
import com.gnsoftware.Ordem.Servico.services.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/tecnicos")
public class TecnicoController {

    @Autowired
    private TecnicoService tecnicoService;

    @PostMapping
    public ResponseEntity<TecnicoDto> save(@Valid @RequestBody TecnicoDto tecnicoDto) {
        return ResponseEntity.status(HttpStatus.OK).body(tecnicoService.save(tecnicoDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TecnicoDto> update(@PathVariable Long id, @RequestBody TecnicoDto tecnicoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tecnicoService.update(id, tecnicoDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TecnicoDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tecnicoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<TecnicoDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(tecnicoService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        tecnicoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Tecnico Deletado!");
    }
}
