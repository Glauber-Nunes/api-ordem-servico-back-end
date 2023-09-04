package com.gnsoftware.Ordem.Servico.services;

import com.gnsoftware.Ordem.Servico.dto.ProdutoDto;
import com.gnsoftware.Ordem.Servico.model.ProdutoEntity;

import java.util.List;

public interface ProdutoService {

    ProdutoDto save(ProdutoDto produtoDto);

    ProdutoDto update(Long id, ProdutoDto produtoDto);

    ProdutoDto findById(Long id);

    List<ProdutoDto> findAll();

    void delete(Long id);

}
