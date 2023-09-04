package com.gnsoftware.Ordem.Servico.controller;

import com.gnsoftware.Ordem.Servico.dto.PermissaoDto;
import com.gnsoftware.Ordem.Servico.services.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {

    @Autowired
    private PermissaoService permissaoService;

    @PostMapping
    public ResponseEntity<PermissaoDto> save(@RequestBody PermissaoDto permissaoDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(permissaoService.save(permissaoDto));
    }
}
