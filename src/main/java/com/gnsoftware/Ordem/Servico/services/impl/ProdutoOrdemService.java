package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.model.compositekey.ProdutoOrdemEntity;
import com.gnsoftware.Ordem.Servico.repository.ProdutoOrdemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoOrdemService {

    @Autowired
    ProdutoOrdemRepository repository;

    public List<ProdutoOrdemEntity> findAll(){
        return repository.findAll();
    }
}
