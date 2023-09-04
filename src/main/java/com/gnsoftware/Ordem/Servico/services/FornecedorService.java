package com.gnsoftware.Ordem.Servico.services;

import com.gnsoftware.Ordem.Servico.dto.FornecedorDto;
import com.gnsoftware.Ordem.Servico.model.FornecedorEntity;

import java.util.List;

public interface FornecedorService {

    FornecedorDto save(FornecedorDto fornecedorDto);

    FornecedorDto update(Long id, FornecedorDto fornecedorDto);

    void delete(Long id);

    FornecedorDto findById(Long id);

   List<FornecedorDto> findAll();
}
