package com.gnsoftware.Ordem.Servico.services;

import com.gnsoftware.Ordem.Servico.dto.TecnicoDto;
import com.gnsoftware.Ordem.Servico.model.TecnicoEntity;

import java.util.List;

public interface TecnicoService {

    TecnicoDto findById(Long id);

    TecnicoDto save(TecnicoDto tecnicoDto);

    TecnicoDto update(Long id, TecnicoDto tecnicoDto);

    void delete(Long id);

    List<TecnicoDto> findAll();
}
