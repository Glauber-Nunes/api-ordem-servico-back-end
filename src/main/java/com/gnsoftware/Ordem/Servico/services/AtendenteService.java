package com.gnsoftware.Ordem.Servico.services;

import com.gnsoftware.Ordem.Servico.dto.AtendenteDto;

import java.util.List;

public interface AtendenteService {
    AtendenteDto save(AtendenteDto atendenteDto);
    AtendenteDto update(Long id, AtendenteDto atendenteDto);
    List<AtendenteDto> findAll();
    void delete(Long id);
    AtendenteDto findById(Long id);
    AtendenteDto replica(Long id);
}
