package com.gnsoftware.Ordem.Servico.controller;

import com.gnsoftware.Ordem.Servico.dto.ProdutoDto;

import com.gnsoftware.Ordem.Servico.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;
    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @PostMapping
    public ResponseEntity<ProdutoDto> save(@Valid @RequestBody ProdutoDto produtoDto,@RequestParam("imagem") MultipartFile imagem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.save(produtoDto,imagem));
    }
    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDto> update(@PathVariable Long id, @RequestBody ProdutoDto produtoDto, @RequestParam("imagem") MultipartFile imagem) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.update(id, produtoDto,imagem));
    }
    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @GetMapping
    public ResponseEntity<List<ProdutoDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.findAll());
    }
    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.findById(id));
    }
    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("ProdutoEntity Deletado");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @GetMapping("/count")
    public ResponseEntity<Long> countProduto(){
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.countProduto());
    }
}
