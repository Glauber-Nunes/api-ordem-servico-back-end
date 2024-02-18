package com.gnsoftware.Ordem.Servico.controller;

import com.gnsoftware.Ordem.Servico.model.UfEntity;
import com.gnsoftware.Ordem.Servico.repository.UfRepository;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ufs")
public class UfController {

    @Autowired
    private UfRepository ufRepository;
    private ResponseEntity<UfEntity> body;

    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @GetMapping
    public ResponseEntity<List<UfEntity>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(ufRepository.findAll());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @GetMapping("/{id}")
    public ResponseEntity<UfEntity> findById(Long id){
        Optional<UfEntity> ufEntity = ufRepository.findById(id);
        ufEntity.orElseThrow(() -> new ModelNotFound("Not Found UF"));
        return ResponseEntity.status(HttpStatus.OK).body(ufEntity.get());
    }

}
