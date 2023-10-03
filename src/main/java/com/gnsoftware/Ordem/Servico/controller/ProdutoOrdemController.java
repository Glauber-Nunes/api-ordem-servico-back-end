package com.gnsoftware.Ordem.Servico.controller;

import com.gnsoftware.Ordem.Servico.model.compositekey.ProdutoOrdemEntity;
import com.gnsoftware.Ordem.Servico.repository.ProdutoOrdemRepository;
import com.gnsoftware.Ordem.Servico.services.impl.ProdutoOrdemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items-produtos")
public class ProdutoOrdemController {

    @Autowired
    ProdutoOrdemService produtoOrdemService;

    public ResponseEntity<List<ProdutoOrdemEntity>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(produtoOrdemService.findAll());
    }
}
