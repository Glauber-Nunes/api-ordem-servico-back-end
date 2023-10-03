package com.gnsoftware.Ordem.Servico.controller;

import com.gnsoftware.Ordem.Servico.dto.ServicoDto;
import com.gnsoftware.Ordem.Servico.model.ServicoEntity;
import com.gnsoftware.Ordem.Servico.services.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;
    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @PostMapping
    public ResponseEntity<ServicoDto> save(@Valid @RequestBody ServicoDto servicoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoService.save(servicoDto));
    }
    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @PutMapping("/{id}")
    public ResponseEntity<ServicoDto> update(@PathVariable Long id, @RequestBody ServicoDto servicoDto) {
        return ResponseEntity.status(HttpStatus.OK).body(servicoService.update(id, servicoDto));
    }
    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @GetMapping("/{id}")
    public ResponseEntity<ServicoDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(servicoService.findById(id));
    }
    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @GetMapping
    public ResponseEntity<List<ServicoDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(servicoService.findAll());
    }
    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        servicoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Servico Deletado");
    }

}
