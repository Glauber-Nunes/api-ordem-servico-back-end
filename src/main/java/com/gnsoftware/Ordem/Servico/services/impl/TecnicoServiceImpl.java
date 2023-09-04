package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.dto.TecnicoDto;
import com.gnsoftware.Ordem.Servico.model.TecnicoEntity;
import com.gnsoftware.Ordem.Servico.repository.TecnicoRepository;
import com.gnsoftware.Ordem.Servico.services.TecnicoService;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TecnicoServiceImpl implements TecnicoService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Override
    public TecnicoDto findById(Long id) {
        Optional<TecnicoEntity> tecnico = tecnicoRepository.findById(id);
        tecnico.orElseThrow(() -> new ModelNotFound("Tecnico Não Encontrado"));

        return new TecnicoDto(tecnico.get());
    }

    @Override
    public List<TecnicoDto> findAll() {
        List<TecnicoEntity> entities = tecnicoRepository.findAll();

        return entities.stream().map(tecnico -> new TecnicoDto(tecnico)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TecnicoDto save(TecnicoDto dto) {

        TecnicoEntity entity = new TecnicoEntity();

        return this.mapperObject(dto, entity);

    }

    @Override
    public TecnicoDto update(Long id, TecnicoDto dto) {

        Optional<TecnicoEntity> entityBanco = tecnicoRepository.findById(id);
        entityBanco.orElseThrow(() -> new ModelNotFound("Tecnico Not Found"));

        return this.mapperObject(dto, entityBanco.get());
    }

    @Override
    public void delete(Long id) {

        Optional<TecnicoEntity> entityBanco = tecnicoRepository.findById(id);
        entityBanco.orElseThrow(() -> new ModelNotFound("Tecnico Not Found"));

        tecnicoRepository.delete(entityBanco.get());
    }

    private TecnicoDto mapperObject(TecnicoDto dto, TecnicoEntity entity) {
        entity.setNome(dto.getNome());
        tecnicoRepository.save(entity);
        return new TecnicoDto(entity);
    }
}
