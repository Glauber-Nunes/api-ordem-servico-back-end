package com.gnsoftware.Ordem.Servico.services;

import com.gnsoftware.Ordem.Servico.dto.ServicoDto;

import java.util.List;

public interface ServicoService {

    ServicoDto save(ServicoDto servicoDto);

    ServicoDto update(Long id, ServicoDto servicoDto);

    ServicoDto findById(Long id);

    List<ServicoDto> findAll();

    void delete(Long id);
}