package com.gnsoftware.Ordem.Servico.services;

import com.gnsoftware.Ordem.Servico.dto.SituacaoOrdemDto;

import java.util.List;

public interface SituacaoOrdemService {

    SituacaoOrdemDto findById(Long ID);

    List<SituacaoOrdemDto> findAll();

    SituacaoOrdemDto update(Long id, SituacaoOrdemDto situacaoOrdemDto);
}
