package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.dto.ServicoDto;
import com.gnsoftware.Ordem.Servico.model.ServicoEntity;
import com.gnsoftware.Ordem.Servico.repository.ServicoRepository;
import com.gnsoftware.Ordem.Servico.services.ServicoService;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicoServiceImpl implements ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Override
    @Transactional
    public ServicoDto save(ServicoDto dto) {

        ServicoEntity entity = new ServicoEntity();

        return this.mapperObject(dto, entity);
    }

    @Override
    @Transactional
    public ServicoDto update(Long id, ServicoDto dto) {

        Optional<ServicoEntity> entityBanco = servicoRepository.findById(id);
        entityBanco.orElseThrow(() -> new ModelNotFound("Serviço Not Found"));

        return this.mapperObject(dto, entityBanco.get());
    }

    @Override
    public ServicoDto findById(Long id) {

        Optional<ServicoEntity> servico = servicoRepository.findById(id);
        servico.orElseThrow(() -> new ModelNotFound("Serviço Not Found"));

        return new ServicoDto(servico.get());

    }

    @Override
    public List<ServicoDto> findAll() {
        List<ServicoEntity> entities = servicoRepository.findAll();

        return entities.stream().map(servico -> new ServicoDto(servico)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {

        if (servicoRepository.existsById(id)) {
            servicoRepository.deleteById(id);
        } else {
            throw new ModelNotFound("Serviço Not Found");
        }

    }

    private ServicoDto mapperObject(ServicoDto dto, ServicoEntity entity) {
        entity.setDescricao(dto.getDescricao());
        entity.setPreco(dto.getPreco());

        servicoRepository.save(entity);

        return new ServicoDto(entity);
    }
}
