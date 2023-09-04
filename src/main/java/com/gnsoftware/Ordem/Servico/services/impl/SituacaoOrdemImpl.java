package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.dto.SituacaoOrdemDto;
import com.gnsoftware.Ordem.Servico.model.SituacaoOrdemEntity;
import com.gnsoftware.Ordem.Servico.repository.SituacaoOrdemRepository;
import com.gnsoftware.Ordem.Servico.services.SituacaoOrdemService;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SituacaoOrdemImpl implements SituacaoOrdemService {

    @Autowired
    private SituacaoOrdemRepository situacaoOrdemRepository;

    @Override
    public SituacaoOrdemDto findById(Long id) {
        Optional<SituacaoOrdemEntity> situacaoOrdem = situacaoOrdemRepository.findById(id);
        situacaoOrdem.orElseThrow(() -> new ModelNotFound("Situação Not Found"));

        return new SituacaoOrdemDto(situacaoOrdem.get());
    }

    @Override
    public List<SituacaoOrdemDto> findAll() {
        List<SituacaoOrdemEntity> entities = situacaoOrdemRepository.findAll();

        return entities.stream().map(situacao -> new SituacaoOrdemDto(situacao)).collect(Collectors.toList());
    }

    @Override
    public SituacaoOrdemDto update(Long id, SituacaoOrdemDto situacaoOrdemDto) {

        Optional<SituacaoOrdemEntity> entityBanco = situacaoOrdemRepository.findById(id);
        entityBanco.orElseThrow(() -> new ModelNotFound("Situaçao not found"));

        SituacaoOrdemEntity entity = SituacaoOrdemEntity.builder()
                .id(entityBanco.get().getId())
                .nome(situacaoOrdemDto.getNome() != null ? situacaoOrdemDto.getNome() : entityBanco.get().getNome())
                .build();

        situacaoOrdemRepository.saveAndFlush(entity);

        return new SituacaoOrdemDto(entity);
    }
}
