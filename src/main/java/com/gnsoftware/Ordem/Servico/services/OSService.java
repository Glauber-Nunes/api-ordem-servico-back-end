package com.gnsoftware.Ordem.Servico.services;

import com.gnsoftware.Ordem.Servico.dto.OsDto;
import com.gnsoftware.Ordem.Servico.model.OsEntity;

import java.util.List;

public interface OSService {

    OsDto save(OsDto OsDto);

    OsDto update(Long id, OsDto OsDto);

    OsDto findById(Long id);

    void delete(Long id);

    List<OsDto> findAll();

    void finalizaOs(Long id);

}
