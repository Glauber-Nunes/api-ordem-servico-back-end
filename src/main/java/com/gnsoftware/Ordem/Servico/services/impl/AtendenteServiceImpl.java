package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.dto.AtendenteDto;
import com.gnsoftware.Ordem.Servico.model.AtendenteEntity;
import com.gnsoftware.Ordem.Servico.model.enums.Perfil;
import com.gnsoftware.Ordem.Servico.repository.AtendenteRepository;
import com.gnsoftware.Ordem.Servico.services.AtendenteService;
import com.gnsoftware.Ordem.Servico.services.exceptions.DataIntegrityViolationException;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AtendenteServiceImpl implements AtendenteService {
    @Autowired
    private AtendenteRepository atendenteRepository;

    @Override
    @Transactional
    public AtendenteDto save(AtendenteDto dto) {

        this.existsByCpf(dto);
        AtendenteEntity entity = new AtendenteEntity();

        this.mapperObject(entity, dto);

        return new AtendenteDto(entity);
    }


    @Override
    public AtendenteDto update(Long id, AtendenteDto dto) {

        Optional<AtendenteEntity> entityBanco = atendenteRepository.findById(id);
        entityBanco.orElseThrow(() -> new ModelNotFound("Atendente Not Found"));

        if (!entityBanco.get().getCpf().equals(dto.getCpf())) {
            this.existsByCpf(dto);
        }

        this.mapperObject(entityBanco.get(), dto);

        return new AtendenteDto(entityBanco.get());
    }

    @Override
    @Transactional
    public List<AtendenteDto> findAll() {

        List<AtendenteEntity> listEntity = atendenteRepository.findAll();

        return listEntity.stream().map(atendente -> new AtendenteDto(atendente)).collect(Collectors.toList());
    }

    @Override
    public AtendenteDto findById(Long id) {
        Optional<AtendenteEntity> atendente = atendenteRepository.findById(id);

        atendente.orElseThrow(() -> new ModelNotFound("Atendente Não Encontrado"));

        return new AtendenteDto(atendente.get());
    }

    @Override
    public void delete(Long id) {

        if (atendenteRepository.existsById(id)) {
            atendenteRepository.deleteById(id);
        } else {
            throw new ModelNotFound("Atendente Não Encontrado");
        }

    }

    private void existsByCpf(AtendenteDto atendente) {
        if (atendenteRepository.existsByCpf(atendente.getCpf())) {
            throw new DataIntegrityViolationException("CPF Ja Cadastrado na base de dados");
        }
    }

    private void mapperObject(AtendenteEntity entity, AtendenteDto dto) {

        entity.setNome(dto.getNome());
        entity.setCpf(dto.getCpf());
        entity.setPerfil(Perfil.ATENDENTE);
        atendenteRepository.save(entity);

    }


}
