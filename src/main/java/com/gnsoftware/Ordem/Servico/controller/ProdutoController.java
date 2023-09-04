package com.gnsoftware.Ordem.Servico.controller;

import com.gnsoftware.Ordem.Servico.dto.ProdutoDto;
import com.gnsoftware.Ordem.Servico.model.ProdutoEntity;
import com.gnsoftware.Ordem.Servico.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoDto> save(@Valid @RequestBody ProdutoDto produtoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.save(produtoDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDto> update(@PathVariable Long id, @RequestBody ProdutoDto produtoDto) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.update(id, produtoDto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("ProdutoEntity Deletado");
    }

}
